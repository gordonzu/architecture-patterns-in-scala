package com.gordonyx

import com.gordonyx.model._
import java.time.LocalDate

class BatchesSpec extends munit.FunSuite {

    def makeBatchAndLine(sku: String, batchQty: Int, lineQty: Int): (Batch, OrderLine) = {
        (
            new Batch("batch-001", sku, batchQty, eta = Some(LocalDate.now())),
            OrderLine("order-123", sku, lineQty) 
        )
    }

    test("allocating to a batch reduces available quantity") {
        val batch = new Batch("batch-001", "SMALL-TABLE", 20, Some(LocalDate.now))
        val line = new OrderLine("order-ref", "SMALL-TABLE", 2)

        allocate(batch, line)
        assert(batch.availableQty == 18)
    }

    test("can allocate if available is greater than required") {
        val (largeBatch, smallLine) = makeBatchAndLine("ELEGANT-LAMP", 20, 2)
        assert(largeBatch.canAllocate(smallLine))
    }

    test("cannot allocate if available is smaller than required") {
        val (smallBatch, largeLine) = makeBatchAndLine("ELEGANT-LAMP", 2, 20)
        assert(smallBatch.canAllocate(largeLine) == false)
    }

    test("can allocate if available is equal to required") {
        val (batch, line) = makeBatchAndLine("ELEGANT-LAMP", 2, 2)
        assert(batch.canAllocate(line))
    }



}


