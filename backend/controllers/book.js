import express from "express";
import mongoose from "mongoose";

const router = express.Router();

import bookdb from "../models/book.js";

export const getBooking = async (req, res) => {
    try {
        const book = await bookdb.find();
        res.json(book);
    } catch (err) {
        res.json({ message: err });
    }
};

export const bookAb = async (req, res) => {
    try {
        const bookData = req.body;
        console.log(bookData);
        const newbook = new bookdb({ ...bookData, Booking_Date: new Date().toISOString() });
        await newbook.save();
        res.status(201).json(newpostdb);
    } catch (error) {
        res.status(400).json({ message: error });
    }
}
export default router;