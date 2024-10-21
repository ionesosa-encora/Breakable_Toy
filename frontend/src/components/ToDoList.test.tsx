import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import ToDoList from './ToDoList';
import { ToDo } from '../types';

const mockToDos: ToDo[] = [
    { id: '1', text: 'Test ToDo 1', priority: 'HIGH', done: false, dueDate: '2024-12-12T12:00:00', createdDate: '2024-10-10T10:00:00' },
    { id: '2', text: 'Test ToDo 2', priority: 'MEDIUM', done: true, dueDate: '2024-11-10T15:30:00', createdDate: '2024-10-09T09:00:00' }
];

test('renders todo items', () => {
    render(<ToDoList toDos={mockToDos} onEdit={() => {}} onDelete={() => {}} onToggleDone={() => {}} onSort={() => {}} sortByPriority={null} sortByDueDate={null} />);

    const todo1 = screen.getByText(/Test ToDo 1/i);
    const todo2 = screen.getByText(/Test ToDo 2/i);

    expect(todo1).toBeInTheDocument();
    expect(todo2).toBeInTheDocument();
});
