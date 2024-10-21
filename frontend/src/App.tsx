import React, { useState, useEffect } from 'react';
import Header from './components/Header';
import Filters from './components/Filters';
import ToDoList from './components/ToDoList';
import Pagination from './components/Pagination';
import Statistics from './components/Statistics';
import ToDoForm from './components/ToDoForm';
import ModalForm from './components/ModalForm';
import ModalConfirm from "./components/ModalConfirm";
import { ToDo, Priority } from './types';
import './App.css';

const App: React.FC = () => {
    const [toDos, setToDos] = useState<ToDo[]>([]);
    const [totalItems, setTotalItems] = useState<number>(0);
    const [showForm, setShowForm] = useState<boolean>(false);
    const [currentToDo, setCurrentToDo] = useState<ToDo | null>(null);
    const [filterName, setFilterName] = useState<string>('');
    const [filterPriority, setFilterPriority] = useState<Priority | ''>('');
    const [filterDone, setFilterDone] = useState<'' | 'Done' | 'Undone'>('');
    const [currentPage, setCurrentPage] = useState<number>(0);
    const [toDosPerPage] = useState<number>(10);
    const [sortByPriority, setSortByPriority] = useState<'asc' | 'desc' | null>(null);
    const [sortByDueDate, setSortByDueDate] = useState<'asc' | 'desc' | null>(null);
    const [showDeleteModal, setShowDeleteModal] = useState<boolean>(false);
    const [toDoToDelete, setToDoToDelete] = useState<string | null>(null);

    const fetchToDos = async () => {
        const priorityFilter = filterPriority !== '' ? `&priority=${filterPriority}` : '';
        const doneFilter = filterDone !== '' ? `&done=${filterDone === 'Done' ? 'true' : 'false'}` : '';
        const nameFilter = filterName ? `&name=${filterName}` : '';
        const sortPriority = sortByPriority ? `&sortByPriority=${sortByPriority}` : '';
        const sortDueDate = sortByDueDate ? `&sortByDueDate=${sortByDueDate}` : '';

        let response;
        try {
            if (priorityFilter === '' && doneFilter === '' && nameFilter === '' && sortPriority === '' && sortDueDate === ''){
                response = await fetch(
                    `http://localhost:9090/todos?page=${currentPage}`
                );
            }else {
                response = await fetch(
                    `http://localhost:9090/todos/filter?page=${currentPage}${priorityFilter}${doneFilter}${nameFilter}${sortPriority}${sortDueDate}`
                );
            }
            const data = await response.json();
            setToDos(data.todos);
            setTotalItems(data.totalItems);
        } catch (error) {
            console.error('Error fetching toDos:', error);
        }
    };

    useEffect(() => {
        fetchToDos();
    }, [filterName, filterPriority, filterDone, currentPage, sortByPriority, sortByDueDate]);

    const handleFilterChange = (name: string, priority: Priority | '', done: '' | 'Done' | 'Undone') => {
        setFilterName(name);
        setFilterPriority(priority);
        setFilterDone(done);
        setCurrentPage(0);
    };

    const handleSortChange = (sortField: 'priority' | 'dueDate') => {
        if (sortField === 'priority') {
            setSortByPriority((prev) => {
                return prev === 'asc' ? 'desc' : prev === 'desc' ? null : 'asc';
            });
        } else if (sortField === 'dueDate') {
            setSortByDueDate((prev) => {
                return prev === 'asc' ? 'desc' : prev === 'desc' ? null : 'asc';
            });
        }
        setCurrentPage(0);
    };

    const handleAddToDo = () => {
        setCurrentToDo(null);
        setShowForm(true);
    };

    const handleEditToDo = (toDo: ToDo) => {
        setCurrentToDo(toDo);
        setShowForm(true);
    };

    const handleSaveToDo = async (savedToDo: ToDo) => {
        try {
            if (currentToDo) {
                await fetch(`http://localhost:9090/todos/${savedToDo.id}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(savedToDo),
                });
            } else {
                const newToDoData = {
                    text: savedToDo.text,
                    priority: savedToDo.priority,
                    dueDate: savedToDo.dueDate || null,
                };
                await fetch('http://localhost:9090/todos', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(newToDoData),
                });
            }
            await fetchToDos();
            setShowForm(false);
        } catch (error) {
            console.error('Error saving toDo:', error);
        }
    };

    const handleToggleDone = async (id: string, done: boolean) => {
        try {
            if (done) {
                await fetch(`http://localhost:9090/todos/${id}/done`, { method: 'POST' });
            } else {
                await fetch(`http://localhost:9090/todos/${id}/undone`, { method: 'PUT' });
            }
            await fetchToDos();
        } catch (error) {
            console.error('Error updating ToDo status:', error);
        }
    };

    const handleDeleteClick = (id: string) => {
        setToDoToDelete(id);
        setShowDeleteModal(true);
    };

    const handleConfirmDelete = async () => {
        if (toDoToDelete) {
            try {
                await fetch(`http://localhost:9090/todos/${toDoToDelete}`, { method: 'DELETE' });
                await fetchToDos();
                setShowDeleteModal(false);
                setToDoToDelete(null); // Reiniciar el estado después de eliminar
            } catch (error) {
                console.error('Error deleting ToDo:', error);
            }
        }
    };

    const handleCloseForm = () => {
        setShowForm(false);
    };

    const paginate = (pageNumber: number) => setCurrentPage(pageNumber);

    return (
        <div className="App">
            <Header />
            <button onClick={handleAddToDo}>+ New ToDo</button>
            <Filters onFilterChange={handleFilterChange} />
            <ToDoList
                toDos={toDos}
                onEdit={handleEditToDo}
                onDelete={handleDeleteClick}
                onToggleDone={handleToggleDone}
                onSort={handleSortChange}
                sortByPriority={sortByPriority}
                sortByDueDate={sortByDueDate}
            />
            <Pagination toDosPerPage={toDosPerPage} totalToDos={totalItems} paginate={paginate} currentPage={currentPage} />
            <ModalForm show={showForm} onClose={handleCloseForm}>
                <ToDoForm existingToDo={currentToDo} onSave={handleSaveToDo} onClose={handleCloseForm} />
            </ModalForm>
            <ModalConfirm
                show={showDeleteModal}
                onClose={() => setShowDeleteModal(false)}
                onConfirm={handleConfirmDelete}
            />
            <Statistics />
        </div>
    );
};

export default App;
