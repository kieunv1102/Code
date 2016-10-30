package vn.ce.sale.ui;

import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.Util;

public class CIDManager extends PhoneStateListener {

	public static final int EVENTS = LISTEN_CELL_LOCATION | LISTEN_SERVICE_STATE | LISTEN_SIGNAL_STRENGTHS
			| LISTEN_DATA_ACTIVITY;
	protected TelephonyManager tMgr;
	private int phoneType;

	public CIDManager(TelephonyManager _tgr) {
		super();
		tMgr = _tgr;
		phoneType = tMgr.getPhoneType();

	}

	@Override
	public void onServiceStateChanged(ServiceState serviceState) {
		updateServiceState(serviceState.getOperatorNumeric());
	}

	@Override
	public void onCellLocationChanged(CellLocation Loc) {

		updateCellLocation(Loc);
		// listener.onReadCellComplete(cID, nID);
		processCellInfo();
	}

	@Override
	public void onDataActivity(int direction) {
		processCellInfo();
	}

	@Override
	public void onDataConnectionStateChanged(int state, int networkType) {
		processCellInfo();
	}

	@Override
	public void onSignalStrengthsChanged(SignalStrength signalStrength) {
		processCellInfo();
	}

	void processCellInfo() {
		int cID = -1;
		int nID = -1;
		// int sID = -1;
		switch (phoneType) {
		case TelephonyManager.PHONE_TYPE_GSM:
			GsmCellLocation gsmCellLoc = (GsmCellLocation) tMgr.getCellLocation();
			if (gsmCellLoc != null) {
				nID = gsmCellLoc.getLac();
				cID = gsmCellLoc.getCid();
				if (cID >= 0) {
					Util.code_cellid = String.valueOf(cID);// (cid
															// >=
															// 0
															// ?
															// cid
															// &
															// 0xffff
															// :
															// -1));
					Util.code_lac = String.valueOf(nID);
					Util.code_mmc = tMgr.getNetworkOperator().substring(0, 3);
					Util.code_mnc = tMgr.getNetworkOperator().substring(3);
				}
			}
			break;
		case TelephonyManager.PHONE_TYPE_CDMA:
			CdmaCellLocation cdmaCellLoc = (CdmaCellLocation) tMgr.getCellLocation();
			if (cdmaCellLoc != null) {
				nID = cdmaCellLoc.getNetworkId();
				cID = cdmaCellLoc.getBaseStationId();
				if (cID >= 0) {
					Util.code_cellid = String.valueOf(cID);
					Util.code_lac = String.valueOf(nID);
				}
			}
			break;
		}
		Util.setupCID();
	}

	void updateServiceState(String operator) {
		String mcc = operator != null && operator.length() >= 3 ? operator.substring(0, 3) : "";
		String mnc = operator != null && operator.length() >= 3 ? operator.substring(3) : "";

		Util.code_mnc = mnc;
		Util.code_mmc = mcc;
	}

	void updateCellLocation(CellLocation location) {
		GsmCellLocation gsmLocation;
		try {
			gsmLocation = (GsmCellLocation) location;
		} catch (ClassCastException e) {
			return;
		}
		;
		if (gsmLocation != null) {
			int cid = gsmLocation != null ? gsmLocation.getCid() : -1;
			Util.isNewDataCID = cid > 0;

		} else
			Util.isNewDataCID = false;
	}

}