$(document).ready(function () {


    var pageNumber = 0;
    var listItems;
    var data;
    var id = -1;
    var productdetailResponse;

    loadProduct();




    document.getElementById("closeform").addEventListener("click", closeForm);



    // Function to open the pop-up form
    function openForm() {
        document.getElementById("popupForm").classList.add("active");
        initilizeButtons();
    }

    function closeForm() {
        document.getElementById("cardContainer").innerHTML = "";
        let pn = pageNumber;
        for (let i = 0; i < pn; i++) {
            pageNumber = i;
            loadProduct();
        }
        document.getElementById("popupForm").classList.remove("active");
    }

    function addClickListnerOnShoeDetails() {
        listItems.forEach((item) => {
            item.addEventListener("click", listItemClicked);
        });
    }

    function listItemClicked(event) {
        const clickedItem = event.target;
        id = clickedItem.id;
        loadProductDetail();
        openForm();
    }


    function loadProductDetail() {
        const jwtToken = getCookie("JWTtoken");

        $.ajax({
            url: `/admin/product/${id}`,
            type: 'POST',
            contentType: 'application/json',
            headers: {
                'Authorization': 'Bearer ' + jwtToken
            },
            success: function (res) {
                productdetailResponse = res.data;
                const response = res.data;
                console.log(response)

                initilizeCrousal(response.images);
                document.getElementById("categoryId").textContent = response.productId;
                document.getElementById("categoryName").textContent = response.name;
                document.getElementById("categoryBrief").textContent = response.description;
                document.getElementById("price").textContent = response.price;
                document.getElementById("discount").textContent = response.discount;
                document.getElementById("categoryOfProduct").textContent = response.category.name;
                document.getElementById("quantity").textContent = response.quantity;
                id = response.id;

                const visibilityText = document.getElementById("visibility");
                if (response.available == true) {
                    visibilityText.textContent = "STOCK AVAILABLE";
                    visibilityText.className = " value publicText";
                } else {
                    visibilityText.textContent = "OUT OF STOCK";
                    visibilityText.className = "value privateText";
                }

            },
            error: function (error) {
                console.log(error);
            }
        });
    }

    function initilizeCrousal(images){

        const indicatorsList = document.querySelector(".carousel-indicators");
        const carouselInner = document.querySelector(".carousel-inner");

        for (let i = 0; i < images.length; i++) {
            // Create a new indicator element
            const newIndicator = document.createElement("li");
            newIndicator.setAttribute("data-target", "#carouselExampleIndicators");
            newIndicator.setAttribute("data-slide-to", i);
            if (i === 0) {
              newIndicator.classList.add("active");
            }
        
            // Append the new indicator to the indicators list
            indicatorsList.appendChild(newIndicator);
        
            // Create a new carousel item (div) and image element
            const newCarouselItem = document.createElement("div");
            newCarouselItem.classList.add("carousel-item");
            if (i === 0) {
              newCarouselItem.classList.add("active");
            }
            const newImage = document.createElement("img");
            newImage.classList.add("d-block", "w-100");
            fetchImageByname(id,newImage,images[i]);
            newImage.setAttribute("alt", "Slide " + (i + 1));
        
            // Append the new image to the carousel item
            newCarouselItem.appendChild(newImage);
        
            // Append the new carousel item to the carousel inner
            carouselInner.appendChild(newCarouselItem);
          }
        
        //fetchImage(response.icon, categoryImage);
    }


    function initilizeButtons() {


        const updateCategoryButton = document.getElementById("updateCategoryButton");
        const deleteCategoryButton = document.getElementById("deleteCategoryButton");
        const changeVisibilityButton = document.getElementById("changeVisibilityButton");
        // changeVisibilityModel = document.getElementById("chandeVisibilityModel");


        //updateCategoryButton.addEventListener("click", updateCategoryform)
        // changeVisibilityButton.addEventListener("click", openModal);
        //deleteCategoryButton.addEventListener("click", deleteCategoryRequest);

    }



    function loadProduct() {
        const url = `/admin/product/all/${pageNumber}`;
        const jwtToken = getCookie("JWTtoken");

        $.ajax({
            url: url,
            type: 'POST',
            headers: {
                'Authorization': 'Bearer ' + jwtToken
            },
            success: function (response) {
                console.log(response);
                if (response.statusCode == "200") {
                    data = response.data;
                    if (data.length < 20) document.getElementById("loadMore").style.display = "none";
                    createCards();
                    listItems = document.querySelectorAll(".openFormBtn");
                    addClickListnerOnShoeDetails();
                    if (id != -1) document.getElementById(id).focus();
                }
                // Handle the response data as needed
            },
            error: function (error) {
                console.error('Error making POST request:', error);
                // Handle any errors that occurred during the request
            }
        });
    }

    function createCards() {
        for (const cardData of data) {
            createCard(cardData);
        }
        pageNumber++;
    }


    function createCard(cardData) {
        const cardContainer = document.getElementById("cardContainer");
        const ii = "card" + cardData.productId;
        const card = document.createElement("div");
        card.className = "card cardCategory";
        card.id = ii;
        card.style.width = "18rem";

        const cardImage = document.createElement("img");
        cardImage.className = "card-img-top categoryImage";
        fetchImage(cardData.productId, cardImage);
        cardImage.alt = "categoryImage";

        const cardBody = document.createElement("div");
        cardBody.className = "card-body";

        const cardTitle = document.createElement("h5");
        cardTitle.className = "card-title";
        cardTitle.textContent = cardData.name;
        if (cardData.available == false) {

            const space = document.createElement("i");
            space.textContent = " ";
            const lock = document.createElement("i");
            lock.className = "fa-solid fa-xmark";
            lock.style = "color: #ff5722;";
            cardTitle.appendChild(space);
            space.appendChild(lock);
        }



        const cardText = document.createElement("p");
        cardText.className = "card-text";
        cardText.style = "max-height: 100px; overflow: hidden;"
        cardText.textContent = cardData.description;


        const div = document.createElement("div");
        div.className = "priceDiscountDiv";


        const cardprice = document.createElement("h3");
        cardprice.className = "cardpriceh3";
        cardprice.textContent = cardData.price;

        const carddiscount = document.createElement("h3");
        carddiscount.className = "carddiscounth3";
        carddiscount.textContent = cardData.discount;

        div.appendChild(cardprice);
        div.appendChild(carddiscount);
        cardBody.appendChild(cardTitle);
        cardBody.appendChild(cardText);
        cardBody.appendChild(div);

        const cardButton = document.createElement("button");
        cardButton.className = "btn btn-primary buttonClickmart openFormBtn mt-2";
        cardButton.textContent = "Show Detail";
        cardButton.id = cardData.productId;

        cardBody.appendChild(cardButton);

        card.appendChild(cardImage);
        card.appendChild(cardBody);

        cardContainer.appendChild(card);
    }


    function fetchImage(id, cardImage) {
        $.ajax({
            url: `/product/image/${id}`,
            method: 'GET',
            xhrFields: {
                responseType: 'blob'
            },
            success: function (blob) {
                const imageUrl = URL.createObjectURL(blob);
                cardImage.src = imageUrl;
            },
            error: function (error) {
                console.log(error);
            }
        });
    }

    function fetchImageByname(id, cardImage,imageName) {
        $.ajax({
            url: `/product/image/${id}/${imageName}`,
            method: 'GET',
            xhrFields: {
                responseType: 'blob'
            },
            success: function (blob) {
                const imageUrl = URL.createObjectURL(blob);
                cardImage.src = imageUrl;
            },
            error: function (error) {
                console.log(error);
            }
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


});