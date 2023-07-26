var pageNumber = 0;
var data;

// Function to create a single card
function createCard(cardData) {
    const cardContainer = document.getElementById("cardContainer");

    const card = document.createElement("div");
    card.className = "card cardCategory";
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
    if(cardData.public == false){
		
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

    const cardButton = document.createElement("a");
    cardButton.href = "#";
    cardButton.className = "btn btn-primary buttonClickmart";
    cardButton.textContent = "Show Detail";

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



