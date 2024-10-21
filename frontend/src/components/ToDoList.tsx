import React from 'react';
import { ToDo } from '../types';
import ToDoItem from './ToDoItem';

interface ToDoListProps {
    toDos: ToDo[];
    onEdit: (toDo: ToDo) => void;
    onDelete: (id: string) => void;
    onToggleDone: (id: string, done: boolean) => void;
    onSort: (sortField: 'priority' | 'dueDate') => void;
    sortByPriority: 'asc' | 'desc' | null;
    sortByDueDate: 'asc' | 'desc' | null;
}

const ToDoList: React.FC<ToDoListProps> = ({ toDos, onEdit, onDelete, onToggleDone, onSort, sortByPriority, sortByDueDate }) => {
    return (
        <div className="todo-list">
            <div className="todo-header">
                <div className="todo-checkbox">Done</div>
                <div className="todo-name">Name</div>
                <div className="todo-priority">
                    Priority
                    <button onClick={() => onSort('priority')}>
                        {sortByPriority === 'asc' ? '▲' : sortByPriority === 'desc' ? '▼' : '↕'}
                    </button>
                </div>
                <div className="todo-due-date">
                    Due Date
                    <button onClick={() => onSort('dueDate')}>
                        {sortByDueDate === 'asc' ? '▲' : sortByDueDate === 'desc' ? '▼' : '↕'}
                    </button>
                </div>
                <div className="todo-actions">Actions</div>
            </div>

            {toDos.length === 0 ? (
                <p>No tasks found</p>
            ) : (
                toDos.map((toDo) => (
                    <ToDoItem
                        key={toDo.id}
                        toDo={toDo}
                        onEdit={onEdit}
                        onDelete={onDelete}
                        onToggleDone={onToggleDone}
                    />
                ))
            )}
        </div>
    );
};

export default ToDoList;
