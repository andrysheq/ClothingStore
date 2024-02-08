document.addEventListener('DOMContentLoaded', function () {
    var dropdowns = document.querySelectorAll('.category-dropdown');

    dropdowns.forEach(function (dropdown) {
        dropdown.addEventListener('click', function () {
            var categoryList = dropdown.nextElementSibling;
            categoryList.classList.toggle('show');
        });

        dropdown.addEventListener('mouseleave', function () {
            var categoryList = dropdown.nextElementSibling;
            categoryList.classList.remove('show');
        });
    });
});

