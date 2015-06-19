package org.nem.ncc.controller.requests;

import org.nem.core.model.*;
import org.nem.core.model.primitive.Amount;
import org.nem.core.serialization.*;
import org.nem.ncc.wallet.*;

import java.util.List;

/**
 * A request containing all information necessary to create a multisig aggregate modification transaction.
 * TODO 20150131 J-G: probably makes sense to have a base class for these
 */
public class MultisigModificationRequest {
	private final WalletName walletName;
	private final WalletPassword password;
	private final Address multisigAddress;
	private final Address issuerAddress;
	private final List<Address> addedCosignatories;
	private final List<Address> removedCosignatories;
	private final MultisigMinCosignatoriesModification minCosignatoriesModification;
	private final int hoursDue;
	private final Amount fee;
	private final Amount multisigFee;
	private final int type;

	/**
	 * Creates a new multisig modification request.
	 *
	 * @param walletName The wallet name.
	 * @param password The wallet password.
	 * @param issuerAddress The sender address.
	 * @param addedCosignatories The list of cosignatory addresses.
	 * @param minCosignatoriesModification The minimum cosignatories modification.
	 * @param hoursDue The number of hours for the transaction to be valid.
	 * @param fee The fee.
	 */
	public MultisigModificationRequest(
			final WalletName walletName,
			final int type,
			final WalletPassword password,
			final Address multisigAddress,
			final Address issuerAddress,
			final List<Address> addedCosignatories,
			final List<Address> removedCosignatories,
			final MultisigMinCosignatoriesModification minCosignatoriesModification,
			final int hoursDue,
			final Amount fee,
			final Amount multisigFee) {
		this.walletName = walletName;
		this.type = type;
		this.password = password;
		this.multisigAddress = multisigAddress;
		this.issuerAddress = issuerAddress;
		this.addedCosignatories = addedCosignatories;
		this.removedCosignatories = removedCosignatories;
		this.minCosignatoriesModification = minCosignatoriesModification;
		this.hoursDue = hoursDue;
		this.fee = fee;
		this.multisigFee = multisigFee;
	}

	/**
	 * Deserializes a multisig modification request.
	 *
	 * @param deserializer The deserializer.
	 */
	public MultisigModificationRequest(final Deserializer deserializer) {
		this.walletName = WalletName.readFrom(deserializer, "wallet");
		this.type = deserializer.readInt("type");
		this.password = WalletPassword.readFrom(deserializer, "password");
		this.multisigAddress = Address.readFrom(deserializer, "account");
		this.issuerAddress = Address.readFromOptional(deserializer, "issuer", AddressEncoding.COMPRESSED);
		this.addedCosignatories = deserializer.readObjectArray("addedCosignatories", obj -> Address.readFrom(obj, "address", AddressEncoding.COMPRESSED));
		this.removedCosignatories =  deserializer.readObjectArray("removedCosignatories", obj -> Address.readFrom(obj, "address", AddressEncoding.COMPRESSED));
		this.minCosignatoriesModification = deserializer.readObject("minCosignatories", MultisigMinCosignatoriesModification::new);
		this.hoursDue = deserializer.readInt("hoursDue");
		this.fee = Amount.readFrom(deserializer, "fee");
		this.multisigFee = Amount.readFrom(deserializer, "multisigFee");
	}

	/**
	 * Gets the wallet name.
	 *
	 * @return The wallet name.
	 */
	public WalletName getWalletName() {
		return this.walletName;
	}

	/**
	 * Gets the sender account id.
	 *
	 * @return The sender account id.
	 */
	public Address getIssuerAddress() {
		return this.issuerAddress;
	}

	/**
	 * Gets list of cosignatories.
	 *
	 * @return The list of cosignatories.
	 */
	public List<Address> getAddedCosignatories() {
		return this.addedCosignatories;
	}

	/**
	 * Gets list of cosignatories.
	 *
	 * @return The list of cosignatories.
	 */
	public List<Address> getRemovedCosignatories() {
		return this.removedCosignatories;
	}

	/**
	 * Gets the min cosignatories modification.
	 *
	 * @return The min cosignatories modification.
	 */
	public MultisigMinCosignatoriesModification getMinCosignatoriesModification() {
		return this.minCosignatoriesModification;
	}

	/**
	 * Gets the hours due.
	 *
	 * @return The hours due.
	 */
	public int getHoursDue() {
		return this.hoursDue;
	}

	/**
	 * Gets the password.
	 *
	 * @return The password.
	 */
	public WalletPassword getPassword() {
		return this.password;
	}

	/**
	 * Gets the fee.
	 *
	 * @return The fee.
	 */
	public Amount getFee() {
		return this.fee;
	}
}
