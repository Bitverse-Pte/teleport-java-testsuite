package com.service;

public class ReqConst {
  public static String debugMemStatsReq =
      "{\"jsonrpc\":\"2.0\",\"method\":\"debug_memStats\",\"params\":[],\"id\":1}";

  public static String personalListAccReq =
          "{\"jsonrpc\":\"2.0\",\"method\":\"personal_listAccounts\",\"params\":[],\"id\":1}";

  public static String ethMaxPriorityFeePerGas =
      "{\"jsonrpc\":\"2.0\",\"method\":\"eth_maxPriorityFeePerGas\",\"params\":[],\"id\":1}";

  public static String ethFeeHistory =
      "{\"jsonrpc\":\"2.0\",\"method\":\"eth_feeHistory\",\"params\":[4, \"latest\", [25, 75]],\"id\":1}";

  public static String ethTransactionLogs =
      "{\"jsonrpc\":\"2.0\",\"method\":\"eth_getTransactionLogs\",\"params\":[\"0x36ace3ea2c8d15d57947f2fa19e812e4710e3ad240351301a642c84d392f7feb\"],\"id\":1}";

  public static String ethCall =
      "{\"jsonrpc\":\"2.0\",\"method\":\"eth_call\",\"params\":[{\"from\":\"0x62b59d0910d4dcd905cfcb268d8f35955f2e2acb\", \"to\":\"0xddd64b4712f7c8f1ace3c145c950339eddaf221d\", \"gas\":\"0x520800\", \"gasPrice\":\"0x55ae82600\", \"value\":\"0x16345785d8a0000\", \"data\": \"0xd46e8dd67c5d32be8d46e8dd67c5d32be8058bb8eb970870f072445675058bb8eb970870f072445675\"}, \"0x0\"],\"id\":1}";

  public static String ethPendingTransactions =
          "{\"jsonrpc\":\"2.0\",\"method\":\"eth_getPendingTransactions\",\"params\":[],\"id\":1}";

  public static String ethProof =
      "{\"jsonrpc\":\"2.0\",\"method\":\"eth_getProof\",\"params\":[\"0x62b59d0910d4dcd905cfcb268d8f35955f2e2acb\",[\"0x0000000000000000000000000000000000000000000000000000000000000000\",\"0x0000000000000000000000000000000000000000000000000000000000000001\"],\"latest\"],\"id\":1}";
}