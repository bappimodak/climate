package com.example.climate.repository

import com.example.climate.model.Coord
import com.example.climate.db.CoordDao

class CoordRepository(private val coordDao: CoordDao) {
    suspend fun insertCoord(coord: Coord){
        coordDao.insert(coord)
    }
}