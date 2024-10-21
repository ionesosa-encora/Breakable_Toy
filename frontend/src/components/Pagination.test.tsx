import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import Pagination from './Pagination';

test('renders pagination with page numbers and navigates correctly', () => {
    const paginateMock = jest.fn();
    render(
        <Pagination
            toDosPerPage={10}
            totalToDos={35}
            paginate={paginateMock}
            currentPage={1}
        />
    );

    const page1 = screen.getByText('1');
    const page2 = screen.getByText('2');
    const page3 = screen.getByText('3');
    const page4 = screen.getByText('4');

    expect(page1).toBeInTheDocument();
    expect(page2).toBeInTheDocument();
    expect(page3).toBeInTheDocument();
    expect(page4).toBeInTheDocument();

    fireEvent.click(page2);
    expect(paginateMock).toHaveBeenCalledWith(1);

    const nextButton = screen.getByText('»');
    fireEvent.click(nextButton);
    expect(paginateMock).toHaveBeenCalledWith(1);

    const prevButton = screen.getByText('«');
    fireEvent.click(prevButton);
    expect(paginateMock).toHaveBeenCalledWith(0);

    const lastButton = screen.getByText('»»');
    fireEvent.click(lastButton);
    expect(paginateMock).toHaveBeenCalledWith(3);
});