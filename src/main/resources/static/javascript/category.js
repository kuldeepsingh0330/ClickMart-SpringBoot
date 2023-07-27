var pageNumber = 0;
var data;
var listItems;

// Function to create a single card
function createCard(cardData) {
    const cardContainer = document.getElementById("cardContainer");
    const ii = "card"+cardData.id;
    const card = document.createElement("div");
    card.className = "card cardCategory";
    card.id = ii;
    card.style.width = "18rem";

    const cardImage = document.createElement("img");
    cardImage.className = "card-img-top categoryImage";
    fetchImage(cardData.icon,cardImage);
    cardImage.alt = "categoryImage";

    const cardBody = document.createElement("div");
    cardBody.className = "card-body";

    const cardTitle = document.createElement("h5");
    cardTitle.className = "card-title";
    cardTitle.textContent = cardData.name;
    if(cardData.isPublic == false){
		
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
    cardButton.id=cardData.id;

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



$(document).ready(function() {
	loadCategory();
});


function loadCategory(){
			const url = `/admin/category/all/${pageNumber}`;
            const jwtToken = getCookie("JWTtoken");

            $.ajax({
                url: url,
                type: 'POST',
                headers: {
                    'Authorization': 'Bearer ' + jwtToken
                },
                success: function(response) {
                    if(response.statusCode == "200"){
						data = response.data;
						console.log(data);
						createCards();	
						listItems = document.querySelectorAll(".openFormBtn");
						addClickListnerOnShoeDetails();
						if(id != -1) document.getElementById(id).focus();
					}
                    // Handle the response data as needed
                },
                error: function(error) {
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


$("#loadMore").on("click", function () {
	loadCategory();	
});


$("#addCategory").on("click", function () {
	const categoryData = {
    name: "Categ Name", 
    color: "Category Color", 
    brief: "Category Brief", 
    icon: "Category Icon",
    priority: 1, 
    isPublic: false
  };
  const jwtToken = getCookie("JWTtoken");

  $.ajax({
    url: '/admin/category/add',
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify(categoryData),
    headers: {
    	'Authorization': 'Bearer ' + jwtToken
	},
    success: function (response) {
      console.log(response); // Handle the successful response here
    },
    error: function (error) {
      console.log(error); // Handle the error response here
    }
  });	
});



// Function to open the pop-up form
function openForm() {
  document.getElementById("popupForm").classList.add("active");
  initilizeButtons();
}

// Function to close the pop-up form
function closeForm() {
	document.getElementById("cardContainer").innerHTML = "";
	let pn= pageNumber;
	for(let i=0;i<pn;i++) {
		pageNumber = i;
		loadCategory();
	}
  document.getElementById("popupForm").classList.remove("active");
}

function addClickListnerOnShoeDetails(){
	listItems.forEach((item) => {
  		item.addEventListener("click", listItemClicked);
	});
}

function loadCategoryDetail(){
	const jwtToken = getCookie("JWTtoken");

  $.ajax({
    url: `/admin/category/${id}`,
    type: 'POST',
    contentType: 'application/json',
    headers: {
    	'Authorization': 'Bearer ' + jwtToken
	},
    success: function (response) {
		
		const categoryImage = document.getElementById("categoryImage");
		fetchImage(response.icon,categoryImage);
document.getElementById("categoryId").textContent = response.id;
document.getElementById("categoryName").textContent = response.name;
document.getElementById("categoryBrief").textContent = response.brief;
document.getElementById("createdAt").textContent = response.createdAt;
document.getElementById("lastupdate").textContent = response.lastUpdate;
document.getElementById("priority").textContent = response.priority;
isPublic = response.isPublic;
id = response.id;
isPublicText();

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


document.getElementById("closeform").addEventListener("click", closeForm);
document.getElementById("openFormBtn").addEventListener("click", openForm);



function initilizeButtons(){


const updateCategoryButton = document.getElementById("updateCategoryButton");
const deleteCategoryButton = document.getElementById("deleteCategoryButton");
const changeVisibilityButton = document.getElementById("changeVisibilityButton");
changeVisibilityModel = document.getElementById("chandeVisibilityModel");



changeVisibilityButton.addEventListener("click", openModal);
deleteCategoryButton.addEventListener("click", deleteCategoryRequest);
	
}

var changeVisibilityModel;
var isPublic;
var id = -1;


function openModal() {
  closeForm();
  document.getElementById("closeConfirmVisibility").addEventListener("click",closeModal);
  document.getElementById("confirmChangeVisibilityBtn").addEventListener("click",changeVisibilityRequest);
  const modalBody = document.getElementById("modalBody");
  if(isPublic) modalBody.textContent = "Are you sure to make this Categoty PRIVATE. After making this category private this category not visible to normal user.";
  else modalBody.textContent = "Are you sure to make this Categoty PUBLIC. After making this category private this category visible to normal user."
  $(changeVisibilityModel).modal("show");
}

// Function to close the modal
function closeModal() {
  $(changeVisibilityModel).modal("hide");
}

function changeVisibilityRequest(){
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

function isPublicText(){
			const visibilityText = document.getElementById("visibility");
if(response.isPublic == true){
visibilityText.textContent = "PUBLIC";
visibilityText.className = " value publicText";	
}else{
visibilityText.textContent = "PRIVATE";
visibilityText.className ="value privateText";		
}
	
}

function deleteCategoryRequest(){
	const jwtToken = getCookie("JWTtoken");

  $.ajax({
    url: `/admin/category/rm/${id}`,
    type: 'DELETE',
    contentType: 'application/json',
    headers: {
    	'Authorization': 'Bearer ' + jwtToken
	},
    success: function (response) { 
		if(id == 1) id++;
		else id--;
		closeForm();	

    },
    error: function (error) {
      console.log(error);
    }
  });
	
}











