package com.example.a70_lolkek;

//public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.DayViewHolder> {
//
//    private List<String> days;
//
//    public CalendarAdapter(List<String> days) {
//        this.days = days;
//    }
//
//    @NonNull
//    @Override
//    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar_day, parent, false);
//        return new DayViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
//        String day = days.get(position);
//        holder.dayTextView.setText(day);
//    }
//
//    @Override
//    public int getItemCount() {
//        return days.size();
//    }
//
//    static class DayViewHolder extends RecyclerView.ViewHolder {
//
//        TextView dayTextView;
//
//        DayViewHolder(@NonNull View itemView) {
//            super(itemView);
//            dayTextView = itemView.findViewById(R.id.calendar_day_text_view);
//        }
//    }
//}
