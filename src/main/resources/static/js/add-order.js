document.addEventListener('DOMContentLoaded', function() {
    function resetDropdowns() {
        console.log("Resetting form dropdowns.");
        const customerSelect = document.getElementById('customerId');
        const statusSelect = document.getElementById('status');

        if (customerSelect) {
            customerSelect.selectedIndex = null;
        }
        if (statusSelect) {
            statusSelect.selectedIndex = null;
        }
    }

    const orderForm = document.getElementById('order-form');
    if (orderForm) {
        const orderSuccess = orderForm.dataset.orderSuccess === 'true' ;
        if (orderSuccess) {
            resetDropdowns();
        }
        else {
            resetDropdowns();
        }
    }

    const clearCartForm = document.getElementById('clear-cart-form');
    if (clearCartForm) {
        const clearCartButton = clearCartForm.querySelector('button');

        if (clearCartButton) {
            clearCartButton.addEventListener('click', function() {

                resetDropdowns();
            });
        }
    }
});