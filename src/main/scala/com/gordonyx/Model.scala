package com.gordonyx.model

import java.time.LocalDate
import scala.collection.mutable

case class OrderLine(orderid: String, sku: String, qty: Int, isAllocated: Option[Boolean])

class Batch(ref: String, sku: String, qty: Int, eta: Option[LocalDate]): 
    var reference:          String = ref 
    var batchSku:           String = sku 
    var batchEta:           Option[LocalDate] = eta 
    var availableQty:       Int = qty 
    var allocations:        mutable.Set[OrderLine] = mutable.Set()

def canAllocate(batch: Batch, line: OrderLine): Boolean = 
    batch.batchSku == line.sku && batch.availableQty >= line.qty

def allocate(batch: Batch, line: OrderLine): OrderLine = 
    batch.availableQty -= line.qty
    line.copy(isAllocated = Some(true)) 
    line

def deallocate(batch: Batch, line: OrderLine): OrderLine = 
    line.isAllocated match {
        case Some(true) =>
            batch.availableQty += line.qty 
            line.copy(isAllocated = Some(false))
            line 
        case _ =>
            line 
    }



