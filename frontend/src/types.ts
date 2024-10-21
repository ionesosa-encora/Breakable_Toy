export type Priority = 'HIGH' | 'MEDIUM' | 'LOW';

export interface ToDo {
    id: string;
    text: string;
    priority: Priority;
    done: boolean;
    dueDate: string | null;
    createdDate: string;
}