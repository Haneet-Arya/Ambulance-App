import express from "express";

import {getDriver} from '../controllers/driver.js';

const router = express.Router();

router.get('/',getDriver);

export default router;