import React, {useState, useEffect, useRef} from 'react';
import { ToDo, Priority } from '../types';

interface ToDoFormProps {
    existingToDo?: ToDo | null;
    onSave: (toDo: ToDo) => void;
    onClose: () => void;
}

const ToDoForm: React.FC<ToDoFormProps> = ({ existingToDo, onSave, onClose }) => {
    const [text, setText] = useState<string>(existingToDo?.text || '');
    const [priority, setPriority] = useState<Priority>(existingToDo?.priority || 'LOW');
    const [date, setDate] = useState<string | null>(
        existingToDo?.dueDate ? new Date(existingToDo.dueDate).toLocaleDateString('en-CA') : null
    );

    const [time, setTime] = useState<string | null>(
        existingToDo?.dueDate
            ? `${new Date(existingToDo.dueDate).getHours().toString().padStart(2, '0')}:${new Date(existingToDo.dueDate).getMinutes().toString().padStart(2, '0')}`
            : null
    );

    useEffect(() => {
        if (existingToDo) {
            setText(existingToDo.text);
            setPriority(existingToDo.priority);
            setDate(
                existingToDo?.dueDate
                    ? `${new Date(existingToDo.dueDate).getFullYear()}-${(new Date(existingToDo.dueDate).getMonth() + 1).toString().padStart(2, '0')}-${new Date(existingToDo.dueDate).getDate().toString().padStart(2, '0')}`
                    : null
            );
            setTime(
                existingToDo?.dueDate
                    ? `${new Date(existingToDo.dueDate).getHours().toString().padStart(2, '0')}:${new Date(existingToDo.dueDate).getMinutes().toString().padStart(2, '0')}`
                    : null
            );
        }
    }, [existingToDo]);

    const handleSave = () => {
        if (text.trim() === '') {
            alert('Task name is required.');
            return;
        }
        if (text.length > 120){
            alert('Max length 120');
            return;
        }
        const dueDate = date && time ? `${date}T${time}:00` : null;

        const newToDo: ToDo = {
            id: existingToDo ? existingToDo.id : Math.random().toString(36).substr(2, 9), // Generamos un id temporal si es nuevo
            text,
            priority,
            done: existingToDo ? existingToDo.done : false,
            dueDate,
            createdDate: existingToDo ? existingToDo.createdDate : new Date().toISOString(),
        };
        console.log("dueDate",dueDate);

        onSave(newToDo);
        onClose();
    };

    return (
        <div className="todo-form">
            <h2>{existingToDo ? 'Edit ToDo' : 'New ToDo'}</h2>
            <input
                type="text"
                placeholder="Task name"
                value={text}
                onChange={(e) => setText(e.target.value)}
            />
            <select value={priority} onChange={(e) => setPriority(e.target.value as Priority)}>
                <option value="HIGH">High</option>
                <option value="MEDIUM">Medium</option>
                <option value="LOW">Low</option>
            </select>
            <input
                type="date"
                value={date || ''}
                onChange={(e) => setDate(e.target.value || null)}
            />
            <input
                type="time"
                value={time || ''}
                onChange={(e) => setTime(e.target.value || null)}
            />
            <div className="todo-form-buttons">
                <button onClick={handleSave}>{existingToDo ? 'Save Changes' : 'Add Task'}</button>
                <button onClick={onClose}>Cancel</button>
            </div>
        </div>
    );
};

export default ToDoForm;