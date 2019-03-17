$('document').ready(function () {
    if (window.location.href.includes("sectors")) {
        document.getElementById("sectorsLink").classList.add("active");
    }
    else if (window.location.href.includes("orders")) {
        document.getElementById("ordersLink").classList.add("active");
    }
    else if (window.location.href.includes("profile")) {
        document.getElementById("profileLink").classList.add("active");
    }
    else {
        document.getElementById("landingLink").classList.add("active");
    }
});