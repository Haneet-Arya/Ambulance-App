import express from "express";

import { getHospital } from '../controllers/hospital.js';

const router = express.Router();

router.get('/', getHospital);

export default router;