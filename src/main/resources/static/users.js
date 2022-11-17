$(async function () {
    await getAllUsers();
});

const table = $('#allUsersTable1')
function getAllUsers() {
    table.empty()
    fetch("http://localhost:8080/api/users")
        .then(response => response.json())
        .then(data => {
            data.forEach(user => {
                let usersTable = `$(
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                        <td>${user.surname}</td>
                        <td>${user.age}</td>
                        <td>${user.email}</td>
                        <td>${user.roles.map(role => role.name)}</td>
                        <td>
                            <button type="button" data-toggle="modal" id="buttonEdit"
                                    data-action="edit" data-id="${user.id}" data-target="#editUser">Edit</button>
                        </td>
                        <td>
                            <button type="button" data-toggle="modal" id="buttonDelete"
                                    data-action="delete" data-id="${user.id}" data-target="#deleteUser">Delete
                            </button>
                        </td>
                    </tr>)`;
                table.append(usersTable);
            })
        })
}


async function getUser(id) {
    let url = "http://localhost:8080/api/users/" + id;
    let response = await fetch(url);
    return await response.json();
}

function getRoles(selector) {
    let collection = selector.selectedOptions
    let roles = []
    for (let i = 0; i < collection.length; i++) {
        if (collection[i].value === '1') {
            roles.push({
                id: 1,
                name: 'ROLE_ADMIN'
            })
        } else if (collection[i].value === '2') {
            roles.push({
                id: 2,
                name: 'ROLE_USER'
            })
        }
    }
    return roles
}


$('#editUser').on('show.bs.modal', e => {
    let button = $(e.relatedTarget);
    let id = button.data('id');
    editModal(id);
})

async function editModal(id) {
    let user = await getUser(id);
    let form = document.forms["formUserEdit"];
    form.id.value = user.id;
    form.username.value = user.username;
    form.surname.value = user.surname;
    form.age.value = user.age;
    form.email.value = user.email;
    form.password.value = user.password;
    form.roles.value = user.roles
}

$(async function() {
    editUsers();
});

async function editUsers() {
    let editForm = document.forms["formUserEdit"]
    editForm.addEventListener("submit", e => {
        e.preventDefault();
        fetch("http://localhost:8080/api/users/", {
            method: 'put',
            headers: {
                'Accept':'application/json',
                'Content-Type':'application/json'
            },
            body: JSON.stringify({
                id: editForm.id.value,
                username: editForm.username.value,
                surname: editForm.surname.value,
                age: editForm.age.value,
                email: editForm.email.value,
                password: editForm.password.value,
                roles: getRoles(document.getElementById('rolesEditUser'))
                })
        }).then(() => {
            $('#editButton').click();
            getAllUsers()
        })
    })
}

$('#deleteUser').on('show.bs.modal', e => {
    let button = $(e.relatedTarget);
    let id = button.data('id');
    deleteModal(id);
})

async function deleteModal(id) {
    let user = await getUser(id);
    let form = document.forms["formUserDelete"];
    form.id.value = user.id;
    form.username.value = user.username;
    form.surname.value = user.surname;
    form.age.value = user.age;
    form.email.value = user.email;
    form.roles.value = user.roles.map(role => role.name);
}

$(async function() {
    deleteUser();
});
function deleteUser(){
    const deleteForm = document.forms["formUserDelete"];
    deleteForm.addEventListener("submit", e => {
        e.preventDefault();
        fetch("http://localhost:8080/api/users/" + deleteForm.id.value, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(() => {
                $('#deleteButton').click();
                getAllUsers();
            })
    })
}

$(async function() {
    await newUser();
});
async function newUser() {
    const form = document.forms["addNewUser"];
    form.addEventListener('submit', addUser)
    function addUser(e) {
        e.preventDefault();
        fetch("http://localhost:8080/api/users", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: form.username.value,
                surname: form.surname.value,
                age: form.age.value,
                email: form.email.value,
                password: form.password.value,
                roles: getRoles(document.getElementById('rolesNewUser'))
            })
        }).then(() => {
            form.reset();
            getAllUsers();
            $('#allUsersTable').click();
        })
    }
}

