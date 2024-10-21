import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import Filters from './Filters';

test('calls onFilterChange with the correct values', () => {
    const onFilterChangeMock = jest.fn();
    render(<Filters onFilterChange={onFilterChangeMock} />);

    const nameInput = screen.getByPlaceholderText('Filter by name');

    const selects = screen.getAllByRole('combobox');
    const prioritySelect = selects[0];
    const statusSelect = selects[1];

    fireEvent.change(nameInput, { target: { value: 'Shopping' } });
    fireEvent.change(prioritySelect, { target: { value: 'HIGH' } });
    fireEvent.change(statusSelect, { target: { value: 'Done' } });

    const applyButton = screen.getByText('Apply Filters');
    fireEvent.click(applyButton);

    expect(onFilterChangeMock).toHaveBeenCalledWith('Shopping', 'HIGH', 'Done');
});