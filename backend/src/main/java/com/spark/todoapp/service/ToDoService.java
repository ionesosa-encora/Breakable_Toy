package com.spark.todoapp.service;

import com.spark.todoapp.model.Priority;
import com.spark.todoapp.model.ToDo;
import com.spark.todoapp.model.ToDoPaginationResponse;
import com.spark.todoapp.repository.ToDoRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;

import java.time.Duration;

@Service
public class ToDoService {

    private final ToDoRepository toDoRepository;

    @Autowired
    public ToDoService(ToDoRepository toDoRepository){
        this.toDoRepository = toDoRepository;
    }

    public ToDoPaginationResponse getAllToDosWithPagination(int page){
        int pageSize = 10;

        List<ToDo> allTodos = toDoRepository.getAllToDos();

        int start = page * pageSize;
        int end = Math.min(start + pageSize, allTodos.size());

        List<ToDo> paginatedTodos = (start > allTodos.size()) ? new ArrayList<>() : allTodos.subList(start, end);

        return new ToDoPaginationResponse(paginatedTodos, allTodos.size());
    }

    public Optional<ToDo> getToDoById(UUID id){
        return toDoRepository.getToDoById(id);
    }

    public ToDo createToDo(String text, Priority priority, LocalDateTime dueDate ){
        ToDo newToDo = new ToDo(text, priority);
        newToDo.setDueDate(dueDate);
        return toDoRepository.createToDo(newToDo);
    }

    public void deleteToDo(UUID id){
        toDoRepository.deleteToDo(id);
    }

    public ToDo updateToDo(UUID id, String text, Priority priority, String dueDate){
        Optional<ToDo> existingToDoOpt = toDoRepository.getToDoById(id);
        if (existingToDoOpt.isPresent()){
            ToDo existingToDo = existingToDoOpt.get();

            existingToDo.setText(text);
            existingToDo.setPriority(priority);
            existingToDo.setDueDate(dueDate != null ? LocalDateTime.parse(dueDate) : null );
            return toDoRepository.updateToDo(existingToDo);
        }
        return null;
    }

    public ToDoPaginationResponse findToDos(String name, Boolean done, Priority priority, int page, String sortByPriority, String sortByDueDate) {
        int pageSize = 10;

        List<ToDo> filteredTodos = new ArrayList<>(toDoRepository.getAllToDos());

        // Filtros
        if (done != null) {
            filteredTodos = filteredTodos.stream()
                    .filter(todo -> todo.isDone() == done)
                    .collect(Collectors.toList());
        }

        if (name != null && !name.isEmpty()) {
            filteredTodos = filteredTodos.stream()
                    .filter(todo -> todo.getText().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (priority != null) {
            filteredTodos = filteredTodos.stream()
                    .filter(todo -> todo.getPriority().equals(priority))
                    .collect(Collectors.toList());
        }

        // Ordenamiento
        Comparator<ToDo> comparator = Comparator.comparing(ToDo::getId);  // Comparador inicial sin efecto

        // Si se envía un ordenamiento por prioridad
        if (sortByPriority != null) {
            Comparator<ToDo> priorityComparator = Comparator.comparing(ToDo::getPriority);
            if ("desc".equalsIgnoreCase(sortByPriority)) {
                priorityComparator = priorityComparator.reversed();
            }
            comparator = priorityComparator;  // Se establece el comparador de prioridad como principal
        }

        // Ordenamiento por fecha de vencimiento
        if (sortByDueDate != null) {
            Comparator<ToDo> dueDateComparator = Comparator.comparing(ToDo::getDueDate, Comparator.nullsLast(Comparator.naturalOrder()));
            if ("desc".equalsIgnoreCase(sortByDueDate)) {
                dueDateComparator = dueDateComparator.reversed();
            }
            // Si ya hay un comparador de prioridad, aplicamos el subordenamiento por fecha
            // Si no hay comparador de prioridad, el orden por fecha será el criterio principal
            comparator = (sortByPriority != null) ? comparator.thenComparing(dueDateComparator) : dueDateComparator;
        }

        // Aplicar el comparador final para ordenar los todos
        filteredTodos.sort(comparator);

        // Paginación
        int start = page * pageSize;
        int end = Math.min(start + pageSize, filteredTodos.size());

        List<ToDo> paginatedTodos = (start > filteredTodos.size()) ? new ArrayList<>() : filteredTodos.subList(start, end);

        return new ToDoPaginationResponse(paginatedTodos, filteredTodos.size());
    }

    public ToDo markAsDone(UUID id){//quiza juntar esto con lo de abajo
        Optional<ToDo> existingToDoOpt = toDoRepository.getToDoById(id);
        if (existingToDoOpt.isPresent()){
            ToDo existingToDo = existingToDoOpt.get();
            if (!existingToDo.isDone()){
                existingToDo.setDone(true);
                existingToDo.setDoneDate(LocalDateTime.now());
                return toDoRepository.updateToDo(existingToDo);
            }
            return existingToDo;
        }
        return null;
    }

    public ToDo markAsUndone(UUID id){
        Optional<ToDo> existingToDoOpt = toDoRepository.getToDoById(id);
        if (existingToDoOpt.isPresent()){
            ToDo existingToDo = existingToDoOpt.get();
            if (existingToDo.isDone()){
                existingToDo.setDone(false);
                //existingToDo.setDueDate(null);
                return toDoRepository.updateToDo(existingToDo);
            }
            return existingToDo;
        }
        return null;
    }

    public Map<String, String> calculateAverageCompletionTime(){
        List<ToDo> completedTodos = toDoRepository.getAllToDos().stream().filter(ToDo::isDone).collect(Collectors.toList());

        double totalAverage = completedTodos.stream()
                .mapToDouble(todo -> Duration.between(todo.getCreatedDate(), todo.getDoneDate()).getSeconds())
                .average()
                .orElse(0);

        Map<Priority, Double> priorityAverages = Arrays.stream(Priority.values())
                .collect(Collectors.toMap(
                        priority -> priority,
                        priority -> completedTodos.stream()
                                .filter(todo -> todo.getPriority().equals(priority))
                                .mapToDouble(todo -> Duration.between(todo.getCreatedDate(), todo.getDoneDate()).getSeconds())
                                .average()
                                .orElse(0)
                ));

        Map<String, String> results = new HashMap<>();
        results.put("Overall Average", formatDuration((long) totalAverage));
        for (Map.Entry<Priority, Double> entry : priorityAverages.entrySet()) {
            results.put(String.format("Average for %s", entry.getKey()), formatDuration(entry.getValue().longValue()));
        }

        return results;
    }

    private String formatDuration(long seconds){
        long hours = seconds / 3600;
        long minutes = seconds % 3600 / 60;
        long remainingSeconds = seconds % 60;

        if (hours > 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
        } else {
            return String.format("%02d:%02d", minutes, remainingSeconds);
        }
    }

}
