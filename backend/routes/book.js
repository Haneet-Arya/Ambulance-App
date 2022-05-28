import express from "express";

import { bookAb , getBooking } from '../controllers/book.js';

const router = express.Router();

router.post('/', bookAb);
router.get('/',getBooking)

export default router;