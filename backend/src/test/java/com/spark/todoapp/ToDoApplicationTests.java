package com.spark.todoapp;
import com.spark.todoapp.service.ToDoService;

import com.spark.todoapp.model.Priority;
import com.spark.todoapp.model.ToDo;
import com.spark.todoapp.model.ToDoPaginationResponse;
import com.spark.todoapp.repository.ToDoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.UUID;
import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ToDoServiceTest {

	@Mock
	private ToDoRepository toDoRepository;

	@InjectMocks
	private ToDoService toDoService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateToDo() {
		String text = "Test ToDo";
		Priority priority = Priority.HIGH;
		ToDo todo = new ToDo(text, priority);

		when(toDoRepository.createToDo(any(ToDo.class))).thenReturn(todo);

		ToDo createdToDo = toDoService.createToDo(text, priority, null);

		assertNotNull(createdToDo);
		assertEquals(text, createdToDo.getText());
		assertEquals(priority, createdToDo.getPriority());
		verify(toDoRepository, times(1)).createToDo(any(ToDo.class));
	}

	@Test
	void testGetToDoById() {
		UUID id = UUID.randomUUID();
		ToDo todo = new ToDo("Test ToDo", Priority.MEDIUM);
		when(toDoRepository.getToDoById(id)).thenReturn(Optional.of(todo));

		Optional<ToDo> result = toDoService.getToDoById(id);

		assertTrue(result.isPresent());
		assertEquals("Test ToDo", result.get().getText());
		verify(toDoRepository, times(1)).getToDoById(id);
	}

	@Test
	void testGetToDoById_NotFound() {
		UUID id = UUID.randomUUID();
		when(toDoRepository.getToDoById(id)).thenReturn(Optional.empty());

		Optional<ToDo> result = toDoService.getToDoById(id);

		assertFalse(result.isPresent());
		verify(toDoRepository, times(1)).getToDoById(id);
	}

	@Test
	void testMarkAsDone() {
		UUID id = UUID.randomUUID();
		ToDo todo = new ToDo("Test ToDo", Priority.LOW);
		when(toDoRepository.getToDoById(id)).thenReturn(Optional.of(todo));
		when(toDoRepository.updateToDo(any(ToDo.class))).thenAnswer(invocation -> invocation.getArgument(0));

		ToDo result = toDoService.markAsDone(id);

		assertTrue(result.isDone());
		assertNotNull(result.getDoneDate());
		verify(toDoRepository, times(1)).getToDoById(id);
		verify(toDoRepository, times(1)).updateToDo(todo);
	}

	@Test
	void testMarkAsUndone() {
		UUID id = UUID.randomUUID();
		ToDo todo = new ToDo("Test ToDo", Priority.HIGH);
		todo.setDone(true);
		when(toDoRepository.getToDoById(id)).thenReturn(Optional.of(todo));
		when(toDoRepository.updateToDo(any(ToDo.class))).thenAnswer(invocation -> invocation.getArgument(0));

		ToDo result = toDoService.markAsUndone(id);

		assertFalse(result.isDone());
		assertNull(result.getDoneDate());
		verify(toDoRepository, times(1)).getToDoById(id);
		verify(toDoRepository, times(1)).updateToDo(todo);
	}

	@Test
	void testDeleteToDo() {
		UUID id = UUID.randomUUID();
		doNothing().when(toDoRepository).deleteToDo(id);

		toDoService.deleteToDo(id);

		verify(toDoRepository, times(1)).deleteToDo(id);
	}

	@Test
	void testFindToDosByName() {
		String nameFilter = "Test";
		ToDo todo1 = new ToDo("Test ToDo 1", Priority.MEDIUM);
		ToDo todo2 = new ToDo("Another ToDo", Priority.HIGH);
		List<ToDo> todos = Arrays.asList(todo1, todo2);
		when(toDoRepository.getAllToDos()).thenReturn(todos);

		ToDoPaginationResponse result = toDoService.findToDos(nameFilter, null, null, 0, null, null);

		assertEquals(1, result.getTodos().size());
		assertEquals("Test ToDo 1", result.getTodos().get(0).getText());
	}

	@Test
	void testFindToDosByPriority() {
		Priority priorityFilter = Priority.HIGH;
		ToDo todo1 = new ToDo("Test ToDo 1", Priority.HIGH);
		ToDo todo2 = new ToDo("Another ToDo", Priority.MEDIUM);
		List<ToDo> todos = Arrays.asList(todo1, todo2);
		when(toDoRepository.getAllToDos()).thenReturn(todos);

		ToDoPaginationResponse result = toDoService.findToDos(null, null, priorityFilter, 0, null, null);

		assertEquals(1, result.getTodos().size());
		assertEquals(Priority.HIGH, result.getTodos().get(0).getPriority());
	}

	@Test
	void testFindToDosByDoneStatus() {
		ToDo todo1 = new ToDo("Test ToDo 1", Priority.LOW);
		todo1.setDone(true);
		ToDo todo2 = new ToDo("Another ToDo", Priority.MEDIUM);
		todo2.setDone(false);
		List<ToDo> todos = Arrays.asList(todo1, todo2);
		when(toDoRepository.getAllToDos()).thenReturn(todos);

		ToDoPaginationResponse result = toDoService.findToDos(null, true, null, 0, null, null);

		assertEquals(1, result.getTodos().size());
		assertTrue(result.getTodos().get(0).isDone());
	}
}