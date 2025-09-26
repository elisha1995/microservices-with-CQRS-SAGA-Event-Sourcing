package com.techbank.account_cmd.api.controller;

import com.techbank.account_cmd.api.command.DepositFundCommand;
import com.techbank.account_common.dto.BaseResponse;
import com.techbank.cqrs_core.exception.AggregateNotFoundException;
import com.techbank.cqrs_core.infrastructure.CommandDispatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/deposit-fund")
public class DepositFundController {

    private final Logger logger = Logger.getLogger(DepositFundController.class.getName());

    private final CommandDispatcher commandDispatcher;

    public DepositFundController(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> depositFund(@PathVariable(value = "id") String id,
                                                    @RequestBody DepositFundCommand command) {
        try {
            command.setId(id);
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("Deposit funds request completed successfully"), HttpStatus.OK);
        } catch (IllegalStateException | AggregateNotFoundException e) {
            logger.log(Level.WARNING, MessageFormat.format("Client made a bad request - {0}", e.toString()));
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErrorMessage = MessageFormat.format("Error while processing request to deposit funds for id - {0}", id);
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
