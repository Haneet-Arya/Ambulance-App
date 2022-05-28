import mongoose from "mongoose";

const bookSchema = new mongoose.Schema({
    name: {
        type: String,
        required: true
    },
    hospital: {
        type: String,
        required: true
    },
    driver: {
        type: String,
        required: true
    },
    driverPhone: {
        type: String,
        required: true
    },
    Booking_Date: {
        type: Date,
        default: new Date()
    }
});

var bookdb = mongoose.model("bookDB", bookSchema);

export default bookdb;