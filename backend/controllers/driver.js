import express from "express";
import mongoose from "mongoose";

const router = express.Router();

import driverdb from "../models/driver.js";

export const getDriver = async (req, res) => {
    try {
        const drivers = await driverdb.find();
        res.status(200).json(drivers);
    } catch (error) {

        res.status(404).json({ message: error });
    }
}

export default router;