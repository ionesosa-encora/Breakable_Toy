import React, { useEffect, useState } from 'react';

interface StatisticsData {
    [key: string]: string;
}

const Statistics: React.FC = () => {
    const [statistics, setStatistics] = useState<StatisticsData | null>(null);

    const fetchStatistics = async () => {
        try {
            const response = await fetch('http://localhost:9090/todos/average-completion-time');
            const data = await response.json();
            setStatistics(data);
        } catch (error) {
            console.error('Error fetching statistics:', error);
        }
    };
    useEffect(() => {
        fetchStatistics();
    }, []);

    console.log('statistics', statistics);
    console.log('Overall Average:', statistics?.['Overall Average']);

    return (
        <div className="statistics">
            <h3>Completion Time Statistics</h3>
            {statistics ? (
                <>
                    <p>General Average Completion Time: {statistics['Overall Average']}</p>
                    <p>High Priority Average: {statistics['Average for HIGH']}</p>
                    <p>Medium Priority Average: {statistics['Average for MEDIUM']}</p>
                    <p>Low Priority Average: {statistics['Average for LOW']}</p>
                </>
            ) : (
                <p>No statistics to show</p>
            )}
            <button onClick={fetchStatistics}>Update Statistics</button>
        </div>
    );
};

export default Statistics;