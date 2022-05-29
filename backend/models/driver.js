import mongoose from "mongoose";

const driverSchema = new mongoose.Schema({
    name: {
        type: String,
        required: true
    },
    phone: {
        type: String,
        required: true
    },
    lat: {
        type: Number,
        required: true
    },
    lng: {
        type: Number,
        required: true
    }
});

var driverdb = mongoose.model("driverDB", driverSchema);

export default driverdb;