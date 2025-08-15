document.addEventListener('DOMContentLoaded', function() {
    const customerSelect = document.getElementById('customerId');
    const statusSelect = document.getElementById('status');
    const orderSelect = document.getElementById('orderId');

    function restoreSelection(selectElement, storageKey) {
        if (!selectElement) return;
        const saved = localStorage.getItem(storageKey);
        if (saved) {
            selectElement.value = saved;
        } else {
            selectElement.selectedIndex = 0;
        }

        selectElement.addEventListener('change', () => {
            localStorage.setItem(storageKey, selectElement.value);
        });
    }

    restoreSelection(customerSelect, 'selectedCustomer');
    restoreSelection(statusSelect, 'selectedStatus');
    restoreSelection(orderSelect, 'selectedOrder');

    function resetAll() {
        if (customerSelect) {
            customerSelect.selectedIndex = 0;
            localStorage.removeItem('selectedCustomer');
        }
        if (statusSelect) {
            statusSelect.selectedIndex = 0;
            localStorage.removeItem('selectedStatus');
        }
        if (orderSelect) {
            orderSelect.selectedIndex = 0;
            localStorage.removeItem('selectedOrder');
        }

        location.reload();
    }

    const clearCartForm = document.getElementById('clear-cart-form');
    if (clearCartForm) {
        clearCartForm.addEventListener('submit', () => {
            localStorage.removeItem('selectedCustomer');
            localStorage.removeItem('selectedStatus');
            localStorage.removeItem('selectedItems');
        });
    }

});