import React from 'react';
import { ToDo } from '../types';

interface ToDoItemProps {
    toDo: ToDo;
    onEdit: (toDo: ToDo) => void;
    onDelete: (id: string) => void;
    onToggleDone: (id: string, done: boolean) => void;
}

const getRowColor = (dueDate: string | null, done: boolean) => {
    if (done) {
        return 'done';
    }

    if (!dueDate) {
        return '';
    }

    const today = new Date();
    const due = new Date(dueDate);
    const diffTime = due.getTime() - today.getTime();
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));

    if (diffDays <= 7) {
        return 'red';
    } else if (diffDays <= 14) {
        return 'yellow';
    } else {
        return 'green';
    }
};

const ToDoItem: React.FC<ToDoItemProps> = ({ toDo, onEdit, onDelete, onToggleDone }) => {
    const handleCheckboxChange = () => {
        onToggleDone(toDo.id, !toDo.done);
    };

    const handleEdit = () => {
        onEdit(toDo);
    };

    const handleDelete = () => {
        onDelete(toDo.id);
    };

    const rowClass = getRowColor(toDo.dueDate, toDo.done);

    return (
        <div className={`todo-item ${rowClass}`}>
            <div className="todo-checkbox">
                <input type="checkbox" checked={toDo.done} onChange={handleCheckboxChange} />
            </div>
            <div className="todo-name">
                {toDo.text}
            </div>
            <div className="todo-priority">
                {toDo.priority}
            </div>
            <div className="todo-due-date">
                {toDo.dueDate
                    ? `${toDo.dueDate.split('T')[0]} ${toDo.dueDate.split('T')[1].substring(0, 5)}`
                    : 'No due date'}
            </div>
            <div className="todo-actions">
                <button onClick={handleEdit}>Edit</button>
                <button onClick={handleDelete}>Delete</button>
            </div>
        </div>
    );
};

export default ToDoItem;