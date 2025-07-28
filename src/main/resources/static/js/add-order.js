// This function waits for the page content to be fully loaded before running.
document.addEventListener('DOMContentLoaded', function() {
    // Find the form with the ID 'details-form'
    const detailsForm = document.getElementById('details-form');
    if (detailsForm) {
        // Find the two dropdowns inside that form
        const customerSelect = document.getElementById('customerId');
        const statusSelect = document.getElementById('status');

        // This function will be called whenever a dropdown changes
        const submitForm = () => {
            detailsForm.submit();
        };

        // Attach the function to the 'change' event of both dropdowns
        if (customerSelect) {
            customerSelect.addEventListener('change', submitForm);
        }
        if (statusSelect) {
            statusSelect.addEventListener('change', submitForm);
        }
    }
});