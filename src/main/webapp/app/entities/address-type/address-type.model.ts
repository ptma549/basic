export interface IAddressType {
  id?: number;
  name?: string | null;
  position?: number | null;
}

export class AddressType implements IAddressType {
  constructor(public id?: number, public name?: string | null, public position?: number | null) {}
}

export function getAddressTypeIdentifier(addressType: IAddressType): number | undefined {
  return addressType.id;
}
