$(document).ready(function () {



  var changeVisibilityModel;
  var isPublic;
  var id = -1;
  var pageNumber = 0;
  var data;
  var listItems;
  var categorydetailResponse;




  loadCategory();



  $("#loadMore").on("click", function () {
    loadCategory();
  });


  $("#addCategory").on("click", function () {
    document.getElementById("addCategorypopupForm").classList.add("active");
    document.getElementById("categoryForm").reset();

    document.getElementById("updateCategoryButtonadd").classList.remove("active");
    document.getElementById("updateCategoryButtonadd").classList.add("inactive");
    document.getElementById("addCategoryButton").classList.remove("inactive");
    document.getElementById("addCategoryButton").classList.add("active");

    document.getElementById("formTitleCategory").textContent = "Add Category";
  });

  $("#closeaddCategoryform").on("click", function () {
    document.getElementById("addCategorypopupForm").classList.remove("active");
  });

  document.getElementById("categoryForm").addEventListener("submit", function (event) {
    event.preventDefault();
    const clickedButton = event.submitter;
    if (clickedButton.id === "addCategoryButton") {
      addCategoryDateCreate();
    } else if (clickedButton.id === "updateCategoryButton") {
      updateCategoryDateCreate();
    }
  });

  document.getElementById("closeform").addEventListener("click", closeForm);


  function createCard(cardData) {
    const cardContainer = document.getElementById("cardContainer");
    const ii = "card" + cardData.id;
    const card = document.createElement("div");
    card.className = "card cardCategory";
    card.id = ii;
    card.style.width = "18rem";

    const cardImage = document.createElement("img");
    cardImage.className = "card-img-top categoryImage";
    fetchImage(cardData.icon, cardImage);
    cardImage.alt = "categoryImage";

    const cardBody = document.createElement("div");
    cardBody.className = "card-body";

    const cardTitle = document.createElement("h5");
    cardTitle.className = "card-title";
    cardTitle.textContent = cardData.name;
    if (cardData.isPublic == false) {

      const space = document.createElement("i");
      space.textContent = " ";
      const lock = document.createElement("i");
      lock.className = "fa-solid fa-lock lockIcon";
      lock.style = "color: #ff5722;";
      cardTitle.appendChild(space);
      space.appendChild(lock);
    }



    const cardText = document.createElement("p");
    cardText.className = "card-text";
    cardText.textContent = cardData.brief;

    const cardButton = document.createElement("button");
    cardButton.className = "btn btn-primary buttonClickmart openFormBtn";
    cardButton.textContent = "Show Detail";
    cardButton.id = cardData.id;

    cardBody.appendChild(cardTitle);
    cardBody.appendChild(cardText);
    cardBody.appendChild(cardButton);

    card.appendChild(cardImage);
    card.appendChild(cardBody);

    cardContainer.appendChild(card);
  }

  // Function to create cards for each object in the data array
  function createCards() {
    for (const cardData of data) {
      createCard(cardData);
    }
    pageNumber++;
  }

  function fetchImage(name, cardImage) {
    $.ajax({
      url: `/categories/category_image/${name}`,
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

  function addCategoryDateCreate() {
    const jwtToken = getCookie("JWTtoken");


    // Convert the form data to a JSON object
    const categoryData = {
      name: document.getElementById("categoryname").value,
      brief: document.getElementById("brief").value,
      priority: document.getElementById("catpriority").value,
      isPublic: document.getElementById("isPublic").value, // Convert to boolean
    };

    // Create a new object to hold both category data and the icon file
    const dataToSend = new FormData();
    dataToSend.append("category", JSON.stringify(categoryData));
    dataToSend.append("iconFile", document.getElementById("icon").files[0]);
    addCategoryRequest(dataToSend, "/admin/category/add", jwtToken);
  }


  function updateCategoryDateCreate() {
    const jwtToken = getCookie("JWTtoken");


    // Convert the form data to a JSON object
    const categoryData = {
      id: id,
      name: document.getElementById("categoryname").value,
      brief: document.getElementById("brief").value,
      priority: document.getElementById("catpriority").value,
      isPublic: document.getElementById("isPublic").value, // Convert to boolean
    };

    // Create a new object to hold both category data and the icon file
    const dataToSend = new FormData();
    dataToSend.append("category", JSON.stringify(categoryData));
    dataToSend.append("iconFile", document.getElementById("icon").files[0]);
    addCategoryRequest(dataToSend, "/admin/category/update", jwtToken);

  }

  function addCategoryRequest(dataToSend, urls, jwtToken) {
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




  // Function to open the pop-up form
  function openForm() {
    document.getElementById("popupForm").classList.add("active");
    initilizeButtons();
  }

  // Function to close the pop-up form
  function closeForm() {
    document.getElementById("cardContainer").innerHTML = "";
    let pn = pageNumber;
    for (let i = 0; i < pn; i++) {
      pageNumber = i;
      loadCategory();
    }
    document.getElementById("popupForm").classList.remove("active");
  }

  function addClickListnerOnShoeDetails() {
    listItems.forEach((item) => {
      item.addEventListener("click", listItemClicked);
    });
  }

  function loadCategoryDetail() {
    const jwtToken = getCookie("JWTtoken");

    $.ajax({
      url: `/admin/category/${id}`,
      type: 'POST',
      contentType: 'application/json',
      headers: {
        'Authorization': 'Bearer ' + jwtToken
      },
      success: function (response) {
        categorydetailResponse = response;

        const categoryImage = document.getElementById("categoryImage");
        fetchImage(response.icon, categoryImage);
        document.getElementById("categoryId").textContent = response.id;
        document.getElementById("categoryName").textContent = response.name;
        document.getElementById("categoryBrief").textContent = response.brief;
        document.getElementById("createdAt").textContent = response.createdAt;
        document.getElementById("lastupdate").textContent = response.lastUpdate;
        document.getElementById("priority").textContent = response.priority;
        isPublic = response.isPublic;
        id = response.id;

        const visibilityText = document.getElementById("visibility");
        if (response.isPublic == true) {
          visibilityText.textContent = "PUBLIC";
          visibilityText.className = " value publicText";
        } else {
          visibilityText.textContent = "PRIVATE";
          visibilityText.className = "value privateText";
        }

      },
      error: function (error) {
        console.log(error);
      }
    });
  }

  function listItemClicked(event) {
    const clickedItem = event.target;
    id = clickedItem.id;
    loadCategoryDetail();
    openForm();
  }

  function initilizeButtons() {


    const updateCategoryButton = document.getElementById("updateCategoryButton");
    const deleteCategoryButton = document.getElementById("deleteCategoryButton");
    const changeVisibilityButton = document.getElementById("changeVisibilityButton");
    changeVisibilityModel = document.getElementById("chandeVisibilityModel");


    updateCategoryButton.addEventListener("click", updateCategoryform)
    changeVisibilityButton.addEventListener("click", openModal);
    deleteCategoryButton.addEventListener("click", deleteCategoryRequest);

  }

  function updateCategoryform() {
    document.getElementById("categoryForm").reset();
    document.getElementById("updateCategoryButtonadd").classList.remove("inactive");
    document.getElementById("updateCategoryButtonadd").classList.add("active");
    document.getElementById("addCategoryButton").classList.remove("active");
    document.getElementById("addCategoryButton").classList.add("inactive");
    document.getElementById("addCategorypopupForm").classList.add("active");
    document.getElementById("categoryname").value = categorydetailResponse.name;
    document.getElementById("brief").value = categorydetailResponse.brief;
    document.getElementById("catpriority").value = categorydetailResponse.priority;
    document.getElementById("isPublic").value = categorydetailResponse.isPublic;
    document.getElementById("formTitleCategory").textContent = "Update Category";


  }

  function openModal() {
    closeForm();
    document.getElementById("closeConfirmVisibility").addEventListener("click", closeModal);
    document.getElementById("confirmChangeVisibilityBtn").addEventListener("click", changeVisibilityRequest);
    const modalBody = document.getElementById("modalBody");
    if (isPublic) modalBody.textContent = "Are you sure to make this Categoty PRIVATE. After making this category private this category not visible to normal user.";
    else modalBody.textContent = "Are you sure to make this Categoty PUBLIC. After making this category private this category visible to normal user."
    $(changeVisibilityModel).modal("show");
  }

  // Function to close the modal
  function closeModal() {
    $(changeVisibilityModel).modal("hide");
    openForm();
  }


  function changeVisibilityRequest() {
    closeModal();
    const jwtToken = getCookie("JWTtoken");

    $.ajax({
      url: `/admin/category/vis/${id}`,
      type: 'PUT',
      contentType: 'application/json',
      headers: {
        'Authorization': 'Bearer ' + jwtToken
      },
      success: function (response) {
        closeForm();
      },
      error: function (error) {
        console.log(error);
      }
    });

  }

  function deleteCategoryRequest() {

    const result = window.confirm("Are you sure to delete this category");

    if (result) {
      const jwtToken = getCookie("JWTtoken");

      $.ajax({
        url: `/admin/category/rm/${id}`,
        type: 'DELETE',
        contentType: 'application/json',
        headers: {
          'Authorization': 'Bearer ' + jwtToken
        },
        success: function (response) {
          if (id == 1) id++;
          else id--;
          closeForm();

        },
        error: function (error) {
          console.log(error);
        }
      });
    }
  }


  function loadCategory() {
    const url = `/admin/category/all/${pageNumber}`;
    const jwtToken = getCookie("JWTtoken");

    $.ajax({
      url: url,
      type: 'POST',
      headers: {
        'Authorization': 'Bearer ' + jwtToken
      },
      success: function (response) {
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

  function getCookie(name) {
    const value = "; " + document.cookie;
    const parts = value.split("; " + name + "=");
    if (parts.length === 2) {
      return parts.pop().split(";").shift();
    }
    return null;
  }



});