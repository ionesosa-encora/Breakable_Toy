import React, { useState } from 'react';
import { Priority } from '../types';

interface FiltersProps {
    onFilterChange: (name: string, priority: Priority | '', done: '' | 'Done' | 'Undone') => void;
}

const Filters: React.FC<FiltersProps> = ({ onFilterChange }) => {
    const [filterName, setFilterName] = useState<string>('');
    const [filterPriority, setFilterPriority] = useState<Priority | ''>('');
    const [filterDone, setFilterDone] = useState<'' | 'Done' | 'Undone'>('');

    const handleFilterChange = () => {
        onFilterChange(filterName, filterPriority, filterDone);
    };

    return (
        <div className="filters">
            <input
                type="text"
                placeholder="Filter by name"
                value={filterName}
                onChange={(e) => setFilterName(e.target.value)}
            />
            <select value={filterPriority} onChange={(e) => setFilterPriority(e.target.value as Priority)}>
                <option value="">All Priorities</option>
                <option value="HIGH">High</option>
                <option value="MEDIUM">Medium</option>
                <option value="LOW">Low</option>
            </select>
            <select value={filterDone} onChange={(e) => setFilterDone(e.target.value as '' | 'Done' | 'Undone')}>
                <option value="">All Tasks</option>
                <option value="Done">Done</option>
                <option value="Undone">Undone</option>
            </select>
            <button onClick={handleFilterChange}>Apply Filters</button>
        </div>
    );
};

export default Filters;