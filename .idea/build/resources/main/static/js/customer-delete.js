document.addEventListener('DOMContentLoaded', () => {
    const deleteButtons = document.querySelectorAll('.delete-btn[data-customer-id]');
    const deleteForm = document.getElementById('deleteCustomerForm');

    deleteButtons.forEach(button => {
        button.addEventListener('click', () => {
            const customerId = button.getAttribute('data-customer-id');
            deleteForm.action = `/customers/${customerId}/delete`;
        });
    });
});
