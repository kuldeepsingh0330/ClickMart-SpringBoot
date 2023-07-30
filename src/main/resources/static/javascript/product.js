$(document).ready(function () {


    var pageNumber = 0;
    var listItems;
    var data;
    var id = -1;
    var productdetailResponse;
    var crousalLength;
    var crousalCurrentPosition = 0;
    var allCategoryName;


    function getAllCategoryname(){
        const jwtToken = getCookie("JWTtoken");
        $.ajax({
            url: "/admin/category/getAllCategoryName",
            type: 'POST',
            headers: {
                'Authorization': 'Bearer ' + jwtToken
            },
            success: function (response) {
                allCategoryName = response;
            },
            error: function (error) {
                console.error('Error making POST request:', error);
            }
        });        
    }

    

    loadProduct();
    getAllCategoryname();

    document.getElementById("closeform").addEventListener("click", closeForm);
    document.getElementById("prevButton").addEventListener("click", prevButton);
    document.getElementById("nextButton").addEventListener("click", nextButton);
    document.getElementById("addCategory").addEventListener("click", addProductform);

    $("#loadMore").on("click", function () {
        loadCategory();
      });

    $("#closeaddCategoryform").on("click", function () {
        document.getElementById("addCategorypopupForm").classList.remove("active");
    });



    document.getElementById("categoryForm").addEventListener("submit", function (event) {
        event.preventDefault();
        const clickedButton = event.submitter;
        if (clickedButton.id === "addProductButton") {
            addCategoryDateCreate();
        } else if (clickedButton.id === "updateProductButton") {
            updateCategoryDateCreate();
        }
    });


    function addCategoryDateCreate() {
        const jwtToken = getCookie("JWTtoken");
    
        const categoryData = {
            id: document.getElementById("producyCat").value
        }
    
    
        const productData = {
            productId: id,
          name: document.getElementById("categoryname").value,
          description: document.getElementById("brief").value,
          price: document.getElementById("catprice").value,
          discount: document.getElementById("catDiscount").value,
          quantity: document.getElementById("catQuantity").value,
          available: document.getElementById("catIsAvailable").value,
          category: categoryData,
        };

        const iconFileData = [];
        for (let i = 0; i < document.getElementById("icon").files.length; i++) {
            iconFileData[i] = document.getElementById("icon").files[i];
        }
    
        // Create a new object to hold both category data and the icon file
        const dataToSend = new FormData();
        dataToSend.append("category", JSON.stringify(productData));
        for (let i = 0; i < iconFileData.length; i++) {
            dataToSend.append("iconFile", iconFileData[i]);
        }
        addProductRequest(dataToSend, "/admin/product/add", jwtToken);
      }
    
    
      function updateCategoryDateCreate() {
        const jwtToken = getCookie("JWTtoken");
        const categoryData = {
            id: document.getElementById("producyCat").value
        }
    
    
        const productData = {
            productId: id,
          name: document.getElementById("categoryname").value,
          description: document.getElementById("brief").value,
          price: document.getElementById("catprice").value,
          discount: document.getElementById("catDiscount").value,
          quantity: document.getElementById("catQuantity").value,
          available: document.getElementById("catIsAvailable").value,
          category: categoryData,
        };

        const iconFileData = [];
        for (let i = 0; i < document.getElementById("icon").files.length; i++) {
            iconFileData[i] = document.getElementById("icon").files[i];
        }
    
        // Create a new object to hold both category data and the icon file
        const dataToSend = new FormData();
        dataToSend.append("category", JSON.stringify(productData));
        for (let i = 0; i < iconFileData.length; i++) {
            dataToSend.append("iconFile", iconFileData[i]);
          }
        addProductRequest(dataToSend, "/admin/product/update", jwtToken);

        
    
      }

      function addProductRequest(dataToSend, urls, jwtToken) {
        $.ajax({
          url: urls,
          type: "POST",
          data: dataToSend,
          processData: false,
          contentType: false,
          headers: {
            'Authorization': 'Bearer ' + jwtToken
          },
          success: function (response) {
            const al = alert(response);
            document.getElementById("addCategorypopupForm").classList.remove("active");
          },
          error: function (error) {
            alert("Error : " + error.responseText);
            console.log(error.responseText);
          }
        });
      }


    function prevButton() {

        let img = document.getElementsByClassName("carousel-item");

        if (crousalCurrentPosition <= 0) {
            crousalCurrentPosition = crousalLength - 1;
            img[0].classList.remove("active");
            img[crousalCurrentPosition].classList.add("active");
        } else {
            crousalCurrentPosition--;
            img[crousalCurrentPosition + 1].classList.remove("active");
            img[crousalCurrentPosition].classList.add("active");
        }

    }

    function nextButton() {
        let img = document.getElementsByClassName("carousel-item");
        console.log(img);

        if (crousalCurrentPosition >= crousalLength - 1) {
            crousalCurrentPosition = 0;
            img[crousalLength - 1].classList.remove("active");
            img[0].classList.add("active");
        } else {
            crousalCurrentPosition++;
            img[crousalCurrentPosition - 1].classList.remove("active");
            img[crousalCurrentPosition].classList.add("active");
        }
    }

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

                document.getElementById("categoryId").textContent = response.productId;
                document.getElementById("categoryName").textContent = response.name;
                document.getElementById("categoryBrief").textContent = response.description;
                document.getElementById("price").textContent = response.price;
                document.getElementById("discount").textContent = response.discount;
                document.getElementById("categoryOfProduct").textContent = response.category.name;
                document.getElementById("quantity").textContent = response.quantity;
                id = response.productId;


                const visibilityText = document.getElementById("visibility");
                if (response.available == true) {
                    visibilityText.textContent = "STOCK AVAILABLE";
                    visibilityText.className = " value publicText";
                } else {
                    visibilityText.textContent = "OUT OF STOCK";
                    visibilityText.className = "value privateText";
                }

                crousalLength = response.images.length;
                initilizeCrousal(response.images);

            },
            error: function (error) {
                console.log(error);
            }
        });
    }

    function initilizeCrousal(images) {

        const indicatorsList = document.querySelector(".carousel-indicators");
        const carouselInner = document.querySelector(".carousel-inner");
        carouselInner.innerHTML = "";

        for (let i = 0; i < images.length; i++) {

            // Create a new carousel item (div) and image element
            const newCarouselItem = document.createElement("div");
            newCarouselItem.classList.add("carousel-item");
            if (i === 0) {
                newCarouselItem.classList.add("active");
            }
            const newImage = document.createElement("img");
            newImage.classList.add("d-block", "w-100");

            fetchImageByname(id, newImage, images[i]);
            newImage.setAttribute("alt", "Slide " + (i + 1));
            newImage.style.height = "500px";

            // Append the new image to the carousel item
            newCarouselItem.appendChild(newImage);

            // Append the new carousel item to the carousel inner
            carouselInner.appendChild(newCarouselItem);
        }
    }


    function initilizeButtons() {


        const updateCategoryButton = document.getElementById("updateCategoryButton");
        const deleteCategoryButton = document.getElementById("deleteCategoryButton");
        const changeVisibilityButton = document.getElementById("changeVisibilityButton");
        // changeVisibilityModel = document.getElementById("chandeVisibilityModel");


        updateCategoryButton.addEventListener("click", updateProductform)
        // changeVisibilityButton.addEventListener("click", openModal);
        //deleteCategoryButton.addEventListener("click", deleteCategoryRequest);

    }

    function updateProductform() {
        document.getElementById("categoryForm").reset();
        document.getElementById("updateProductButton").classList.remove("inactive");
        document.getElementById("updateProductButton").classList.add("active");
        document.getElementById("addProductButton").classList.remove("active");
        document.getElementById("addProductButton").classList.add("inactive");
        document.getElementById("addCategorypopupForm").classList.add("active");
        document.getElementById("categoryname").value = productdetailResponse.name;
        document.getElementById("brief").value = productdetailResponse.description;
        document.getElementById("catprice").value = productdetailResponse.price;
        document.getElementById("catQuantity").value = productdetailResponse.quantity;
        document.getElementById("catIsAvailable").value = productdetailResponse.available;
        document.getElementById("formTitleCategory").textContent = "Update Product";
        document.getElementById("catDiscount").value = productdetailResponse.discount;
        setSelectOptions();
        document.getElementById("producyCat").value = productdetailResponse.category.id

    }


    function addProductform() {
        document.getElementById("categoryForm").reset();
        document.getElementById("updateProductButton").classList.remove("active");
        document.getElementById("updateProductButton").classList.add("inactive");
        document.getElementById("addProductButton").classList.remove("inactive");
        document.getElementById("addProductButton").classList.add("active");
        document.getElementById("addCategorypopupForm").classList.add("active");
        document.getElementById("formTitleCategory").textContent = "Add Product";
        setSelectOptions()

    }

    function setSelectOptions() {
        const selectElement = $('#producyCat');
    
        selectElement.empty();
    
        $.each(allCategoryName, function(index, currCat) {
          const optionElement = $('<option>').text(currCat[0]).val(currCat[1]);
          selectElement.append(optionElement);
        });
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

    function fetchImageByname(id, cardImage, imageName) {
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