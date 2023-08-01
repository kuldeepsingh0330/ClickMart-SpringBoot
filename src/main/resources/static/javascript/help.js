$(document).ready(function () {

    var pageNumber = 0;

    loadHelp();

    $("#loadMore").on("click", function () {
        loadHelp();
      });


    function loadHelp() {
        const url = `/admin/help/${pageNumber}`;
        const jwtToken = getCookie("JWTtoken");

        $.ajax({
            url: url,
            type: 'POST',
            headers: {
                'Authorization': 'Bearer ' + jwtToken
            },
            success: function (response) {
                console.log(response);
                if (response.statusCode === "200") {
                    if (response.data.length < 20) document.getElementById("loadMore").style.display = "none";
                    generateAccordionItems(response.data);
                }
            },
            error: function (error) {
                console.error('Error making POST request:', error);
                // Handle any errors that occurred during the request
            }
        });
    }


    function generateAccordionItems(feedbackData) {
        const accordionContainer = document.getElementById("accordionExample");

        feedbackData.forEach((feedback) => {
            const accordionItem = document.createElement("div");
            accordionItem.classList.add("accordion-item");

            const accordionHeader = document.createElement("h2");
            accordionHeader.classList.add("accordion-header");

            const accordionButton = document.createElement("button");
            accordionButton.classList.add("accordion-button");
            accordionButton.setAttribute("type", "button");
            accordionButton.setAttribute("data-bs-toggle", "collapse");
            accordionButton.setAttribute("data-bs-target", `#collapse${feedback.id}`);
            accordionButton.setAttribute("aria-expanded", "false");
            accordionButton.setAttribute("aria-controls", `collapse${feedback.id}`);
            accordionButton.textContent = `${feedback.title}`;
            accordionHeader.appendChild(accordionButton);

            const accordionBody = document.createElement("div");
            accordionBody.classList.add("accordion-collapse", "collapse");
            accordionBody.setAttribute("id", `collapse${feedback.id}`);
            accordionBody.setAttribute("data-bs-parent", "#accordionExample");

            const accordionBodyContent = document.createElement("div");
            accordionBodyContent.classList.add("accordion-body");
            accordionBodyContent.textContent = feedback.description;
            accordionBody.appendChild(accordionBodyContent);

            const accordionBodyButton = document.createElement("button");
            accordionBodyButton.classList.add("accordion-body");
            accordionBodyButton.textContent = "Reply";
            accordionBody.appendChild(accordionBodyButton);



            accordionItem.appendChild(accordionHeader);
            accordionItem.appendChild(accordionBody);

            accordionContainer.appendChild(accordionItem);
        });
        pageNumber++;
    }


    function getCookie(name) {
        const value = "; " + document.cookie;
        const parts = value.split("; " + name + "=");
        if (parts.length === 2) {
            return parts.pop().split(";").shift();
        }
        return null;
    }
});