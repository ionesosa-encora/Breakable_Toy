import React from 'react';
import { render, screen } from '@testing-library/react';
import App from './App';

test('renders header component', () => {
  render(<App />);
  const headerElement = screen.getByText(/ToDo App/i);
  expect(headerElement).toBeInTheDocument();
});

test('renders new todo button', () => {
  render(<App />);
  const newTodoButton = screen.getByText(/\+ New ToDo/i);
  expect(newTodoButton).toBeInTheDocument();
});