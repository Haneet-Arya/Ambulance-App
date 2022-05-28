import express from "express";
import mongoose from "mongoose";
import hospitaldb from "../models/hospital.js";

const router = express.Router();


export const getHospital = async (req, res) => {
    try {
        const hospitals = await hospitaldb.find();
        res.status(200).json(hospitals);
    } catch (error) {

        res.status(404).json({ message: error });
    }
}

export default router;