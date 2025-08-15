document.addEventListener('DOMContentLoaded', function () {
    const deleteModal = document.getElementById('deleteConfirmationModal');
    if (deleteModal) {
        deleteModal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            const orderId = button.getAttribute('data-order-id');
            const form = document.getElementById('deleteOrderForm');
            form.action = `/orders/${orderId}/delete`;
        });
    }
});