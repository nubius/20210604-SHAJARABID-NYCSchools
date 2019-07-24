package com.example.sharma.cnycschools;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sharma.cnycschools.data.NYCSchoolContract;
import com.example.sharma.cnycschools.model.*;


public class SchoolListAdapter extends RecyclerView.Adapter<SchoolListAdapter.SchoolViewHolder> {

    //region Variables
    private final SchoolAdapterOnClickHandler mClickHandler;
    private final Context mContext;
    private Cursor cursor;
    private static final String CLASS_NAME = SchoolListAdapter.class.getSimpleName();
    //endregion

    //region Constructor & Interface
    public SchoolListAdapter(Context context, SchoolAdapterOnClickHandler clickHandler) {
        this.mContext = context;
        mClickHandler = clickHandler;
    }

    public interface SchoolAdapterOnClickHandler {
        void onClick(com.example.sharma.cnycschools.model.SchoolList openAir);
    }
    //endregion

    //region Private Methods

    //endregion

    //region Override
    @Override
    public SchoolViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            View view = layoutInflater.inflate(R.layout.school_list, parent, false);
            return new SchoolViewHolder(view);
        } catch (Exception e) {
            Log.e(CLASS_NAME, "onCreateViewHolder(ViewGroup parent, int viewType) - " + e.getMessage());
        }
        return null;
    }

    @Override
    public void onBindViewHolder(SchoolViewHolder holder, int position) {
        try {
            cursor.moveToPosition(position);

            String schoolName = cursor.getString(cursor.getColumnIndex(NYCSchoolContract.NYCSchoolList.SCHOOL_NAME));
            holder.tvSchoolName.setText(schoolName);
            String address = cursor.getString(cursor.getColumnIndex(NYCSchoolContract.NYCSchoolList.ADDRESS));
            String city = cursor.getString(cursor.getColumnIndex(NYCSchoolContract.NYCSchoolList.CITY));
            int zipCode = cursor.getInt(cursor.getColumnIndex(NYCSchoolContract.NYCSchoolList.ZIP));
            String stateCode = cursor.getString(cursor.getColumnIndex(NYCSchoolContract.NYCSchoolList.STATE_CODE));
            if (!city.equals(""))
                address = String.format("%s, %s", address, city);
            if (!stateCode.equals(""))
                address = String.format("%s, %s", address, stateCode);
            if (zipCode != 0)
                address = String.format("%s, %s", address, zipCode);

            if (address.equals("")) holder.tvAddress.setVisibility(View.GONE);
            else {
                holder.tvAddress.setText(getSpannableData(mContext.getResources().getString(R.string.address), address)); ///getspannabledata is making recyclerview recycle weired.
                //  holder.tvAddress.setText(String.format("%s %s", holder.tvAddress.getText().toString().trim(), address));
            }

            final String phoneNumber = cursor.getString(cursor.getColumnIndex(NYCSchoolContract.NYCSchoolList.PHONE_NUMBER));
            if (phoneNumber.equals("")) holder.tvPhoneNumber.setVisibility(View.GONE);
            else {
                holder.tvPhoneNumber.setText(getSpannableData(mContext.getResources().getString(R.string.phone), phoneNumber));
                //holder.tvPhoneNumber.setText(String.format("%s %s", mContext.getResources().getString(R.string.phone), phoneNumber));
                holder.tvPhoneNumber.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:+1" + phoneNumber));
                            mContext.startActivity(intent);
                        } catch (Exception e) {
                            Log.e(CLASS_NAME + "call -> onClick(View v)", e.getMessage());
                        }
                    }
                });
            }
            final double latitude = cursor.getDouble(cursor.getColumnIndex(NYCSchoolContract.NYCSchoolList.LATITUDE));
            final double logitude = cursor.getDouble(cursor.getColumnIndex(NYCSchoolContract.NYCSchoolList.LONGITUDE));
            if (latitude != 0.0 && logitude != 0.0) {
                holder.tvNavigateAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Uri geoLocation = Uri.parse("geo:" + latitude + "," + logitude);

                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(geoLocation);

                            if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                                mContext.startActivity(intent);
                            } else {
                                Log.d(CLASS_NAME, "Couldn't call " + geoLocation.toString() + ", no receiving apps installed!");
                            }
                        } catch (Exception e) {
                            Log.e(CLASS_NAME, "onBindViewHolder(OpenAirViewHolder holder, int position) - setOnClickListener" + e.getMessage());
                        }
                    }
                });
            }
        } catch (Exception e) {
            Log.e(CLASS_NAME, "onBindViewHolder(OpenAirViewHolder holder, int position) - " + e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        try {
            if (null != cursor)
                return cursor.getCount();
            else
                return 0;
        } catch (Exception e) {
            Log.e(CLASS_NAME, "getItemCount() - " + e.getMessage());
        }
        return 0;
    }
    //endregion

    //region Private Method
    private SpannableString getSpannableData(String fieldName, String fieldValue) {
        //SpannableString spannableString = new SpannableString("");
        try {
            String spannableText = String.format("%s %s", fieldName, fieldValue);
            SpannableString spannableString = new SpannableString(spannableText);
            spannableString.setSpan(new RelativeSizeSpan(.9f), fieldName.length(), spannableText.length(), 0); // set size
            spannableString.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.colorGrayDark)), fieldName.length(), spannableText.length(), 0);// set color
            return spannableString;
        } catch (Exception e) {
            Log.e(CLASS_NAME, "getSpannableData(String fieldName, String fieldValue) - " + e.getMessage());
        }
        return new SpannableString("");
    }
    //endregion

    //region Public Methods
    public void swapCursor(Cursor newCursor) {
        try {
            this.cursor = newCursor;
            notifyDataSetChanged();
        } catch (Exception e) {
            Log.e(CLASS_NAME, "swapCursor(Cursor newCursor) - " + e.getMessage());
        }
    }
    //endregion

    //region View Holder
    public class SchoolViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView tvSchoolName;
        public final TextView tvAddress;
        public final TextView tvPhoneNumber;
        public final TextView tvNavigateAddress;


        public SchoolViewHolder(View view) {
            super(view);
            tvSchoolName = view.findViewById(R.id.schoolNameTV);
            tvAddress = view.findViewById(R.id.addressTV);
            tvPhoneNumber = view.findViewById(R.id.phoneNumberTV);
            tvNavigateAddress = view.findViewById(R.id.navigateAddressTV);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            cursor.moveToPosition(getAdapterPosition());
            SchoolList schoolList = new SchoolList(cursor.getString(cursor.getColumnIndex(NYCSchoolContract.NYCSchoolList.DBN)),
                    cursor.getString(cursor.getColumnIndex(NYCSchoolContract.NYCSchoolList.SCHOOL_NAME)),
                    cursor.getString(cursor.getColumnIndex(NYCSchoolContract.NYCSchoolList.SCHOOL_OVERVIEW)),
                    cursor.getString(cursor.getColumnIndex(NYCSchoolContract.NYCSchoolList.PHONE_NUMBER)),
                    cursor.getString(cursor.getColumnIndex(NYCSchoolContract.NYCSchoolList.EMAIL_ADDRESS)),
                    cursor.getString(cursor.getColumnIndex(NYCSchoolContract.NYCSchoolList.CITY)),
                    cursor.getInt(cursor.getColumnIndex(NYCSchoolContract.NYCSchoolList.TOTAL_STUDENT)),
                    cursor.getString(cursor.getColumnIndex(NYCSchoolContract.NYCSchoolList.START_TIME)),
                    cursor.getString(cursor.getColumnIndex(NYCSchoolContract.NYCSchoolList.END_TIME)),
                    cursor.getString(cursor.getColumnIndex(NYCSchoolContract.NYCSchoolList.WEBSITE)),
                    cursor.getString(cursor.getColumnIndex(NYCSchoolContract.NYCSchoolList.ADDRESS)),
                    cursor.getInt(cursor.getColumnIndex(NYCSchoolContract.NYCSchoolList.ZIP)),
                    cursor.getString(cursor.getColumnIndex(NYCSchoolContract.NYCSchoolList.STATE_CODE)),
                    cursor.getDouble(cursor.getColumnIndex(NYCSchoolContract.NYCSchoolList.LATITUDE)),
                    cursor.getDouble(cursor.getColumnIndex(NYCSchoolContract.NYCSchoolList.LONGITUDE)));
            mClickHandler.onClick(schoolList);
        }
    }
//endregion
}
