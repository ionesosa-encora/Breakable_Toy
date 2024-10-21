import React from 'react';

interface ModalConfirmProps {
    show: boolean;
    onClose: () => void;
    onConfirm: () => void;
}

const ModalConfirm: React.FC<ModalConfirmProps> = ({ show, onClose, onConfirm }) => {
    if (!show) return null;

    return (
        <div className="modal-backdrop">
            <div className="modal-content">
                <h2>Confirm Deletion</h2>
                <p>Do you want to delete this task? This action cannot be reversed.</p>
                <div className="modal-buttons">
                    <button onClick={onConfirm}>Delete</button>
                    <button onClick={onClose}>Cancel</button>
                </div>
            </div>
        </div>
    );
};

export default ModalConfirm;