import React, { useState } from "react"
import { addRoom } from "../utils/ApiFunctions"
import { Link } from "react-router-dom"
const AddRoom = () => {
  const [newRoom, setNewRoom] = useState({
    photo: null,
    roomType: "",
    roomPrice: ""
  })

  const [successMessage, setSuccessMessage] = useState("")
  const [errorMessage, setErrorMessage] = useState("")

  const handleRoomInputChange = (e) => {
    const name = e.target.name
    let value = e.target.value
    if (name === "roomPrice") {
      if (!isNaN(value)) {
        value = parseInt(value)
      } else {
        value = ""
      }
    }
    setNewRoom({ ...newRoom, [name]: value })
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const success = await addRoom(newRoom.roomType, newRoom.roomPrice);
      if (success !== null) {
        setSuccessMessage("A new room was added successfully !");
        setNewRoom({ roomType: "", roomPrice: "" });
        setErrorMessage("");
      } else {
        setErrorMessage("Error adding new room");
      }
    } catch (error) {
      setErrorMessage(error.message);
    }
    setTimeout(() => {
      setSuccessMessage("");
      setErrorMessage("");
    }, 3000);
};


  return (
	<>
	<section className="container mt-5 mb-5">
		<div className="row justify-content-center">
		<div className="col-md-8 col-lg-6">
			<h2 className="mt-5 mb-2">Add a New Room</h2>
			{successMessage && (
			<div className="alert alert-success fade show"> {successMessage}</div>
			)}
  
			{errorMessage && <div className="alert alert-danger fade show"> {errorMessage}</div>}
  
			<form onSubmit={handleSubmit}>
			<div className="mb-3">
				<label htmlFor="roomType" className="form-label">
				Room Type
				</label>
				<select
				required
				className="form-control"
				id="roomType"
				name="roomType"
				value={newRoom.roomType}
				onChange={handleRoomInputChange}
				>
				<option value="">Select a room type</option>
				<option value="Balcony Suite">Luxury</option>
				<option value="Suite">Suite</option>
				</select>
			</div>
			<div className="mb-3">
				<label htmlFor="roomPrice" className="form-label">
				Room Price
				</label>
				<input
				required
				type="number"
				className="form-control"
				id="roomPrice"
				name="roomPrice"
				value={newRoom.roomPrice}
				onChange={handleRoomInputChange}
				/>
			</div>
			<div className="d-grid gap-2 d-md-flex mt-2">
				<Link to={"/existing-rooms"} className="btn btn-outline-info">
				Existing rooms
				</Link>
				<button type="submit" className="btn btn-outline-primary ml-5">
				Save Room
				</button>
			</div>
			</form>
		</div>
		</div>
	</section>
	</>
  )
  
}

export default AddRoom;
