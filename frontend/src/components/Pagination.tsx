import React from 'react';

interface PaginationProps {
    toDosPerPage: number;
    totalToDos: number;
    paginate: (pageNumber: number) => void;
    currentPage: number;
}

const Pagination: React.FC<PaginationProps> = ({ toDosPerPage, totalToDos, paginate, currentPage }) => {
    const totalPages = Math.ceil(totalToDos / toDosPerPage);
    currentPage += 1;

    const generatePageNumbers = () => {
        const pageNumbers: (number | string)[] = [];
        const maxPagesToShow = 1;

        if (totalToDos > 0) {
            pageNumbers.push(1);
        }

        if (currentPage > maxPagesToShow + 2) {
            pageNumbers.push('...');
        }

        for (let i = Math.max(2, currentPage - maxPagesToShow); i <= Math.min(totalPages - 1, currentPage + maxPagesToShow); i++) {
            pageNumbers.push(i);
        }

        if (currentPage < totalPages - maxPagesToShow - 1) {
            pageNumbers.push('...');
        }

        if (totalPages > 1) {
            pageNumbers.push(totalPages);
        }

        return pageNumbers;
    };

    const pageNumbers = generatePageNumbers();

    return (
        <nav>
            <ul className="pagination">

                {currentPage > 1 && (
                    <li className="page-item page-arrow">
                        <a onClick={() => paginate(0)} href="#!" className="page-link">
                            &laquo;&laquo;
                        </a>
                    </li>
                )}


                {currentPage > 1 && (
                    <li className="page-item page-arrow">
                        <a onClick={() => paginate(currentPage - 2)} href="#!" className="page-link">
                            &laquo;
                        </a>
                    </li>
                )}

                {pageNumbers.map((number, index) => (
                    <li
                        key={index}
                        className={`page-item ${currentPage === number ? 'active' : ''}`}
                    >
                        {typeof number === 'number' ? (
                            <a onClick={() => paginate(number - 1)} href="#!" className="page-link">
                                {number}
                            </a>
                        ) : (
                            <span className="page-link">{number}</span>
                        )}
                    </li>
                ))}

                {currentPage < totalPages && (
                    <li className="page-item page-arrow">
                        <a onClick={() => paginate(currentPage)} href="#!" className="page-link">
                            &raquo;
                        </a>
                    </li>
                )}

                {currentPage < totalPages && (
                    <li className="page-item page-arrow">
                        <a onClick={() => paginate(totalPages - 1)} href="#!" className="page-link">
                            &raquo;&raquo;
                        </a>
                    </li>
                )}
            </ul>
        </nav>
    );
};

export default Pagination;