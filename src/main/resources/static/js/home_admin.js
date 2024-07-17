function toggleSidebar() {
    const sidebar = document.getElementById('sidebar');
    const mainContainer = document.querySelector('.main-content');

    if (window.innerWidth <= 576) {
        // Toggle sidebar visibility for mobile screens
        if (sidebar.classList.contains('open')) {
            sidebar.classList.remove('open');
            sidebar.classList.add('closed');
            mainContainer.style.marginLeft = '0';
        } else {
            sidebar.classList.remove('closed');
            sidebar.classList.add('open');
            mainContainer.style.marginLeft = '0'; // Adjust for mobile screens
        }
    } else {
        // Toggle sidebar visibility for larger screens
        if (sidebar.classList.contains('closed')) {
            sidebar.classList.remove('closed');
            sidebar.classList.add('open');
            mainContainer.style.marginLeft = '250px'; // Adjust for larger screens
        } else {
            sidebar.classList.remove('open');
            sidebar.classList.add('closed');
            mainContainer.style.marginLeft = '0';
        }
    }
}

window.addEventListener('resize', function() {
    const sidebar = document.getElementById('sidebar');
    const mainContainer = document.querySelector('.main-content');

    if (window.innerWidth > 576) {
        sidebar.classList.remove('closed');
        sidebar.classList.add('open');
        mainContainer.style.marginLeft = '250px'; // Adjust for larger screens
    } else {
        sidebar.classList.add('closed');
        sidebar.classList.remove('open');
        mainContainer.style.marginLeft = '0'; // Adjust for mobile screens
    }
});


function toggleDropdown(event) {
    event.preventDefault();
    const dropdown = event.currentTarget.nextElementSibling;
    dropdown.classList.toggle('show');
}