
const form = document.getElementById("productForm");
const products = document.getElementById("products");

let inventory = [];

function renderInventory() {
    products.innerHTML = "";

    inventory.forEach((item, index) => {
        const row = document.createElement("tr");

        [item.name, item.category || "-", 
         "₹" + item.price.toFixed(2), item.stock].forEach(value => {
            const cell = document.createElement("td");
            cell.textContent = value;
            row.appendChild(cell);
        });

        const action = document.createElement("td");
        const button = document.createElement("button");
        button.textContent = "Delete";
        button.className = "delete-btn";

        button.onclick = () => {
            inventory.splice(index, 1);
            renderInventory();
        };

        action.appendChild(button);
        row.appendChild(action);
        products.appendChild(row);
    });

    const units = inventory.reduce((sum, item) => sum + item.stock, 0);
    const value = inventory.reduce(
        (sum, item) => sum + item.price * item.stock, 0
    );

    document.getElementById("units").textContent = units;
    document.getElementById("value").textContent =
        "₹" + value.toFixed(2);
}

form.addEventListener("submit", event => {
    event.preventDefault();

    inventory.push({
        name: document.getElementById("name").value.trim(),
        category: document.getElementById("category").value.trim(),
        price: Number(document.getElementById("price").value),
        stock: Number(document.getElementById("stock").value)
    });

    renderInventory();
    form.reset();
});

renderInventory();
