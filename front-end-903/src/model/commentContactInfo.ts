export class CommentContactInfo {
  id: number | null;
  value: string;
  orderNumber: number | null;
  createdBy: string | null;
  createdDate: string | null;
  receiverId: number | null;
  contactInfoId: number;

  constructor({
                value,
                contactInfoId,
                id = null,
                orderNumber = null,
                createdBy = null,
                createdDate = null,
                receiverId = null
              }: {
    value: string;
    contactInfoId: number;
    id?: number | null;
    orderNumber?: number | null;
    createdBy?: string | null;
    createdDate?: string | null;
    receiverId?: number | null;
  }) {
    this.value = value;
    this.contactInfoId = contactInfoId;
    this.id = id;
    this.orderNumber = orderNumber;
    this.createdBy = createdBy;
    this.createdDate = createdDate;
    this.receiverId = receiverId;
  }
}
