$(document).ready(function () {

    var pageNumber = 0;
    var id;


    loadTransaction();

    document.getElementById("closeform").addEventListener("click", function () {
        document.getElementById("popupForm").classList.remove("active");
    });

    function loadTransaction() {
        const url = `/admin/order/${pageNumber}`;
        const jwtToken = getCookie("JWTtoken");

        $.ajax({
            url: url,
            type: 'POST',
            headers: {
                'Authorization': 'Bearer ' + jwtToken
            },
            success: function (response) {
                if (response.statusCode === "200") {
                    console.log(response.data)
                    if (response.data.length < 20) document.getElementById("loadMore").style.display = "none";
                    addTransactionDataIntoList(response.data);
                    setClickListnerOnListItem();
                }
            },
            error: function (error) {
                console.error('Error making POST request:', error);
                // Handle any errors that occurred during the request
            }
        });
    }

    $("#loadMore").on("click", function () {
        loadTransaction();
    });

    function openForm() {
        document.getElementById("popupForm").classList.add("active");
    }

    function setClickListnerOnListItem() {
        let allItem = document.querySelectorAll(".dataRow");
        allItem.forEach((item) => {
            item.addEventListener("click", listItemClicked);
        });
    }

    function listItemClicked(event) {
        const clickedItem = event.target;
        const parentElement = clickedItem.parentNode;
        id = parentElement.id;
        loadtransactionDetail();
        openForm();
    }

    function setTextContentAndColor(elementId, text, color) {
        const element = document.getElementById(elementId);
        if (element) {
            element.textContent = text;
            element.style.color = color;
        }
    }

    function formatDate(dateString) {
        const date = new Date(dateString);
        const formattedDate = date.toLocaleDateString(undefined, {
            year: "numeric",
            month: "long",
            day: "numeric",
            hour: "2-digit",
            minute: "2-digit",
            second: "2-digit",
            hour12: false,
        });
        return formattedDate;
    }



    function loadtransactionDetail() {
        const jwtToken = getCookie("JWTtoken");

        $.ajax({
            url: `/admin/order/id/${id}`,
            type: 'POST',
            contentType: 'application/json',
            headers: {
                'Authorization': 'Bearer ' + jwtToken
            },
            success: function (response) {
                console.log(response)
                if (response.statusCode === "200") {
                    let data = response.data;

                    setTextContentAndColor("orderId", data.order_id, "black");
                    setTextContentAndColor("amount", data.amount, "black");
                    setTextContentAndColor("amountPaid", data.amount_paid, "green");
                    setTextContentAndColor("notes", data.notes || "N/A", "black");
                    setTextContentAndColor("createdAt", formatDate(data.created_at), "black");
                    setTextContentAndColor("amountDue", data.amount_due, "red");
                    setTextContentAndColor("currency", data.currency, "black");
                    setTextContentAndColor("receipt", data.receipt, "black");
                    setTextContentAndColor("entity", data.entity, "black");
                    setTextContentAndColor("offerId", data.offer_id || "N/A", "black");
                    setTextContentAndColor("status", data.status === "created" ? "FAILED" : "PAID", data.status === "created" ? "red" : "green");
                    setTextContentAndColor("attempts", data.attempts, "black");
                    setTextContentAndColor("paymentId", data.paymentId || "N/A", "black");
                    setTextContentAndColor("userId", data.user_id, "black");
                    setTextContentAndColor("userPhoneNumber", data.user_phoneNumber, "black");
                    setTextContentAndColor("userName", data.user_name, "black");
                    setTextContentAndColor("userEmail", data.user_emailId, "black");
                    setTextContentAndColor("username", data.username, "black");
                    setTextContentAndColor("orderedProducts", data.orderedProduct , "black");
                }

            },
            error: function (error) {
                console.log(error);
            }
        });

    }

    function addTransactionDataIntoList(data) {
        const transactionTableBody = document.getElementById("transactionTableBody");

        data.forEach(transaction => {
            const row = document.createElement("tr");
            row.id = transaction.id;
            row.classList.add("dataRow")

            const usernameCell = document.createElement("td");
            usernameCell.textContent = transaction.username;

            const orderIDCell = document.createElement("td");
            orderIDCell.textContent = transaction.order_id;

            const amountCell = document.createElement("td");
            amountCell.textContent = `INR ${transaction.amount.toFixed(2)/100}`;

            const statusCell = document.createElement("td");
            if (transaction.status === "paid") {
                statusCell.textContent = "PAID"
                statusCell.style.color = "green"
                statusCell.style.fontWeight = "bold"

            } else {
                statusCell.textContent = "FAILED"
                statusCell.style.color = "red"
                statusCell.style.fontWeight = "bold"
            }

            const paymentIdCell = document.createElement("td");
            paymentIdCell.textContent = transaction.paymentId || "N/A";


            row.appendChild(usernameCell);
            row.appendChild(orderIDCell);
            row.appendChild(amountCell);
            row.appendChild(statusCell);
            row.appendChild(paymentIdCell)
            transactionTableBody.appendChild(row);
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