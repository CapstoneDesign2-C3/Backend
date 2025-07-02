document.addEventListener('DOMContentLoaded', function () {
    loadMap();
    addCLickEventToMenu();
});

function loadMap() {
    var container = document.getElementById('map');
    var options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 3
    };

    var map = new kakao.maps.Map(container, options);
}

function addCLickEventToMenu() {
    const menuOptions = document.querySelectorAll('.map-sidebar-menu-option');
    const tabContents = document.querySelectorAll('.tab-content');

    menuOptions.forEach(option => {
        option.addEventListener('click', function () {
            // 모든 메뉴에서 selected 제거
            menuOptions.forEach(el => el.classList.remove('selected'));
            tabContents.forEach(content => content.style.display = 'none');
            this.classList.add('selected');
            const targetId = this.getAttribute('data-target');
            document.getElementById(targetId).style.display = 'block';
        });
    });
}
