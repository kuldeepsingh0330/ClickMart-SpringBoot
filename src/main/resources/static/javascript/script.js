function handleButtonClick() {
    document.getElementById("apiButton").click();
}

function callAPI() {
    // Retrieve the JWT token from localStorage (assuming it was stored there after login)
    const jwtToken = getCookie("JWTtoken");

    // Make the API call with the JWT token in the headers
    fetch("/admin/category", {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + jwtToken,
        },
    })
        .then((data) => {
            window.location.href = data.url
        })
        .catch((error) => {
            console.error("Error:", error);
        });
}

function getCookie(name) {
    const value = "; " + document.cookie;
    const parts = value.split("; " + name + "=");
    if (parts.length === 2) {
        return parts.pop().split(";").shift();
    }
    return null;
}

$(document).ready(function() {


document.getElementById("logoutNav").addEventListener("click",logout);
function logout(){

    const jwtToken = getCookie("JWTtoken");

    // Make the API call with the JWT token in the headers
    fetch("/admin/logout", {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + jwtToken,
        },
    })
        .then((data) => {
            window.location.href = data.url
        })
        .catch((error) => {
            console.error("Error:", error);
        });
	
}


});








