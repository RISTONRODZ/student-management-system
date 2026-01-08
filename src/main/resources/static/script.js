const API = "http://localhost:8080/students";

let editMode = false;
let editingRollNo = null;

window.onload = async () => {
    await loadStudents();
};

async function loadStudents() {
    const res = await fetch(API);
    const data = await res.json();

    const table = document.getElementById("studentTable");
    table.innerHTML = "";

    data.forEach(s => {
        table.innerHTML += `
            <tr>
                <td>${s.rollNo}</td>
                <td>${s.firstName} ${s.lastName}</td>
                <td>${s.email}</td>
                <td>${s.dob}</td>
                <td>
                    <button class="btn btn-warning btn-sm me-1" onclick="editStudent(${s.rollNo})">Edit</button>
                    <button class="btn btn-danger btn-sm" onclick="deleteStudent(${s.rollNo})">Delete</button>
                </td>
            </tr>
        `;
    });
}

// SAVE / UPDATE
document.getElementById("saveBtn").onclick = async () => {
    const rollNo = document.getElementById("rollNo").value;

    const student = {
        rollNo: Number(rollNo),
        firstName: document.getElementById("firstName").value,
        lastName: document.getElementById("lastName").value,
        email: document.getElementById("email").value,
        dob: document.getElementById("dob").value
    };

    // Validation Check
    if (!rollNo || !student.firstName || !student.email) {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Please fill in all required fields!',
        });
        return;
    }

    let res;

    try {
        if (editMode) {
            res = await fetch(`${API}/${editingRollNo}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(student)
            });
        } else {
            res = await fetch(API, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(student)
            });
        }

        if (!res.ok) {
            Swal.fire({
                icon: 'error',
                title: 'Request failed',
                text: 'The server rejected the request.',
            });
            return;
        }

        // Success Popup
        Swal.fire({
            icon: 'success',
            title: editMode ? 'Updated!' : 'Saved!',
            text: editMode ? 'Student updated successfully.' : 'Student added successfully.',
            timer: 1500,
            showConfirmButton: false
        });

        resetForm();
        await loadStudents();
    } catch (error) {
        Swal.fire('Error', 'Connection to server failed', 'error');
    }
};

// EDIT
async function editStudent(rollNo) {
    const res = await fetch(API);
    const students = await res.json();
    const s = students.find(st => st.rollNo === rollNo);

    document.getElementById("rollNo").value = s.rollNo;
    document.getElementById("rollNo").disabled = true;
    document.getElementById("firstName").value = s.firstName;
    document.getElementById("lastName").value = s.lastName;
    document.getElementById("email").value = s.email;
    document.getElementById("dob").value = s.dob;

    editMode = true;
    editingRollNo = rollNo;

    document.getElementById("saveBtn").innerText = "Update";
    document.getElementById("cancelBtn").classList.remove("d-none");

    // Tiny toast to show editing started
    const Toast = Swal.mixin({
        toast: true,
        position: 'top-end',
        showConfirmButton: false,
        timer: 1000
    });
    Toast.fire({
        icon: 'info',
        title: 'Editing Student ' + rollNo
    });
}

// DELETE
async function deleteStudent(rollNo) {
    // Enhanced Confirmation Dialog
    Swal.fire({
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#dc3545',
        cancelButtonColor: '#6c757d',
        confirmButtonText: 'Yes, delete it!'
    }).then(async (result) => {
        if (result.isConfirmed) {
            await fetch(`${API}/${rollNo}`, { method: "DELETE" });

            Swal.fire(
                'Deleted!',
                'Student record has been deleted.',
                'success'
            );
            await loadStudents();
        }
    });
}

// CANCEL UPDATE
document.getElementById("cancelBtn").onclick = () => {
    resetForm();
};

function resetForm() {
    document.getElementById("rollNo").value = "";
    document.getElementById("rollNo").disabled = false;
    document.getElementById("firstName").value = "";
    document.getElementById("lastName").value = "";
    document.getElementById("email").value = "";
    document.getElementById("dob").value = "";

    editMode = false;
    editingRollNo = null;

    document.getElementById("saveBtn").innerText = "Save";
    document.getElementById("cancelBtn").classList.add("d-none");
}