import java.io.*;
import java.util.*;

public class MyJourneyApp {

    // فئة تمثل المربع أو الكيلو في رحلة الإنجاز
    static class GoalSquare implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private int id;
        private String reward;
        private boolean isCompleted;
        private String note;
        private String completedDate;

        public GoalSquare(int id, String reward) {
            this.id = id;
            this.reward = reward;
            this.isCompleted = false;
            this.note = "";
            this.completedDate = "";
        }

        public int getId() { return id; }
        public String getReward() { return reward; }
        public void setReward(String reward) { this.reward = reward; }
        public boolean isCompleted() { return isCompleted; }
        public void setCompleted(boolean completed) { isCompleted = completed; }
        public String getNote() { return note; }
        public void setNote(String note) { this.note = note; }
        public String getCompletedDate() { return completedDate; }
        public void setCompletedDate(String completedDate) { this.completedDate = completedDate; }
    }

    // فئة إدارة بيانات المستخدم والتقدم
    static class UserData implements Serializable {
        private static final long serialVersionUID = 1L;

        private String personalGoal = "الوصول للوزن المثالي بصحة وحب للذات 🎀";
        private double initialWeight = 84.0;
        private double targetWeight = 54.0;
        private double currentWeight = 84.0;
        private List<GoalSquare> squares = new ArrayList<>();

        public UserData() {
            initDefaultSquares();
        }

        // إعداد الـ 30 مكافأة المجانية
        private void initDefaultSquares() {
            String[] defaultRewards = {
                "الرقص على أغنيتي المفضلة للمتعة 🎶",
                "الرسم والتلوين 🎨",
                "اللعب بلعبتي المفضلة 🎮",
                "مشاهدة فيلم أحبه 🍿",
                "مشاهدة حلقة من مسلسلي المفضل 📺",
                "مشاهدة الأنمي المفضل لدي ✨",
                "مشاهدة فيلم كرتون ممتع 🎬",
                "الاستماع إلى القرآن الكريم براحة بال 🕊️",
                "الاستماع إلى شيء هادئ أحبه 🎧",
                "الكتابة في مذكراتي 📝",
                "تجربة تسريحة شعر جديدة 💇‍♀️",
                "تخصيص وقت لهوايتي المفضلة 🌸",
                "تعلم مهارة جديدة ممتعة 💡",
                "مشاهدة مقاطع ممتعة ومضحكة 📲",
                "الاسترخاء والراحة دون أعمال منزلية 🛋️",
                "تجربة رسم بأسلوب جديد 🖌️",
                "تصميم شيء جميل باستخدام كانفا 💻",
                "لعب لعبة ألغاز ذكية 🧩",
                "تخصيص وقت للقراءة 📖",
                "مشاهدة فيديو تعليمي عن شيء أحبه 🔍",
                "ممارسة حركة خفيفة وممتعة 🧘‍♀️",
                "ترتيب زاوية صغيرة خاصة بي ✨",
                "الاستماع إلى قائمة أغنياتي المفضلة 🎵",
                "مشاهدة فيلم قديم أحبه 🎞️",
                "كتابة قصة قصيرة من خيالي ✍️",
                "تجربة وصفة فنية أو أشغال يدوية 🧶",
                "تخصيص وقت لنفسي بعيدًا عن الانشغالات ☕",
                "تجربة هواية جديدة لا تحتاج إلى مال 🌿",
                "مشاهدة حلقة إضافية من مسلسل أحبه ⭐️",
                "اختيار أي نشاط ممتع أحبه في ذلك اليوم 🎉"
            };

            for (int i = 0; i < defaultRewards.length; i++) {
                squares.add(new GoalSquare(i + 1, defaultRewards[i]));
            }
        }

        public String getPersonalGoal() { return personalGoal; }
        public void setPersonalGoal(String personalGoal) { this.personalGoal = personalGoal; }
        public double getInitialWeight() { return initialWeight; }
        public double getTargetWeight() { return targetWeight; }
        public double getCurrentWeight() { return currentWeight; }
        public void setCurrentWeight(double currentWeight) { this.currentWeight = currentWeight; }
        public List<GoalSquare> getSquares() { return squares; }

        public int getCompletedCount() {
            int count = 0;
            for (GoalSquare sq : squares) {
                if (sq.isCompleted()) count++;
            }
            return count;
        }
    }

    private static final String DATA_FILE = "journey_data.ser";
    private static UserData userData = new UserData();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadData();

        while (true) {
            printHeader();
            System.out.println("🎀 --- القائمة الرئيسية --- 🎀");
            System.out.println("1. عرض جدول المربعات (30 مربع)");
            System.out.println("2. تحديث الهدف الشخصي والوزن الحالي");
            System.out.println("3. عرض صفحة مكافآتي والسجل");
            System.out.println("4. إضافة مربع جديد");
            System.out.println("5. إعادة تعيين الجدول (البدء من جديد)");
            System.out.println("6. حفظ والخروج");
            System.out.print("\nإختر خياراً (1-6): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    showGridMenu();
                    break;
                case "2":
                    updateGoalAndWeight();
                    break;
                case "3":
                    showMyRewards();
                    break;
                case "4":
                    addNewSquare();
                    break;
                case "5":
                    resetData();
                    break;
                case "6":
                    saveData();
                    System.out.println("\nتم حفظ بياناتك بنجاح! نراكِ على خير 🎀✨");
                    return;
                default:
                    System.out.println("\nخيار غير صحيح، حاولي مجدداً 🌸");
            }
        }
    }

    private static void printHeader() {
        System.out.println("\n=======================================================");
        System.out.println("              🎀 رحلتي وإنجازاتي 🎀                  ");
        System.out.println("=======================================================");
        System.out.println("الهدف الشخصي: " + userData.getPersonalGoal());
        System.out.println("الوزن الابتدائي: " + userData.getInitialWeight() + " كجم | الوزن الهدف: " + userData.getTargetWeight() + " كجم");
        System.out.println("الوزن الحالي المسجل: " + userData.getCurrentWeight() + " كجم");
        
        int completed = userData.getCompletedCount();
        int total = userData.getSquares().size();
        double percent = total > 0 ? ((double) completed / total) * 100 : 0;

        System.out.printf("الإنجاز: %d من %d مربعاً مكتمل (%.1f%%)\n", completed, total, percent);
        System.out.println("=======================================================\n");
    }

    private static void showGridMenu() {
        while (true) {
            System.out.println("\n--- 🌸 جدول المربعات والمكافآت 🌸 ---");
            List<GoalSquare> squares = userData.getSquares();

            for (GoalSquare sq : squares) {
                String status = sq.isCompleted() ? "[✔ مكتمل 💕]" : "[⚪ غير مكتمل]";
                System.out.printf("%2d. %s - المكافأة: %s\n", sq.getId(), status, sq.getReward());
            }

            System.out.print("\nادخلي رقم المربع للتحكم به (أو اختاري 0 للعودة): ");
            String input = scanner.nextLine().trim();

            try {
                int sqId = Integer.parseInt(input);
                if (sqId == 0) break;

                GoalSquare selected = null;
                for (GoalSquare sq : squares) {
                    if (sq.getId() == sqId) {
                        selected = sq;
                        break;
                    }
                }

                if (selected != null) {
                    handleSquareInteraction(selected);
                } else {
                    System.out.println("رقم المربع غير موجود!");
                }
            } catch (NumberFormatException e) {
                System.out.println("يرجى إدخال رقم صحيح!");
            }
        }
    }

    private static void handleSquareInteraction(GoalSquare sq) {
        System.out.println("\n-------------------------------------------------------");
        System.out.println("📌 المربع رقم: " + sq.getId());
        System.out.println("الحالة الحالية: " + (sq.isCompleted() ? "مكتمل 💕" : "غير مكتمل 🌸"));
        System.out.println("المكافأة: " + sq.getReward());
        if (!sq.getNote().isEmpty()) {
            System.out.println("ملاحظتك: " + sq.getNote());
        }
        System.out.println("-------------------------------------------------------");

        System.out.println("1. إيه، أنجزته! 🎉");
        System.out.println("2. ليس بعد 🌸");
        System.out.println("3. تعديل نص المكافأة ✏️");
        System.out.println("4. إضافة/تعديل ملاحظة 📝");
        System.out.println("5. العودة للخلف");
        System.out.print("إختر خياراً: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                sq.setCompleted(true);
                sq.setCompletedDate(new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                System.out.println("\n✨ أحسنتِ! خصصتِ وقتًا لنفسك اليوم 🎀");
                System.out.println("🎉 استمتعي بمكافأتك: " + sq.getReward());
                saveData();
                break;
            case "2":
                sq.setCompleted(false);
                System.out.println("\nولا يهمك! كل شيء في وقته المناسب، خذي وقتك وكوني لطيفة مع نفسك 🌸");
                saveData();
                break;
            case "3":
                System.out.print("ادخلي نص المكافأة الجديد: ");
                String newReward = scanner.nextLine().trim();
                if (!newReward.isEmpty()) {
                    sq.setReward(newReward);
                    System.out.println("تم تحديث المكافأة بنجاح! 🎈");
                    saveData();
                }
                break;
            case "4":
                System.out.print("اكتبي ملاحظتك القصيرة: ");
                String note = scanner.nextLine().trim();
                sq.setNote(note);
                System.out.println("تم حفظ الملاحظة! 📝");
                saveData();
                break;
            case "5":
                break;
            default:
                System.out.println("خيار غير صحيح.");
        }
    }

    private static void updateGoalAndWeight() {
        System.out.println("\n--- 📝 تعديل الهدف والوزن ---");
        System.out.print("اكتبي هدفك الشخصي الجديد (دوسي Enter للإبقاء الحالي): ");
        String goalInput = scanner.nextLine().trim();
        if (!goalInput.isEmpty()) {
            userData.setPersonalGoal(goalInput);
        }

        System.out.print("سجلي وزنك الحالي كجم (اختياري - دوسي Enter للتخطي): ");
        String weightInput = scanner.nextLine().trim();
        if (!weightInput.isEmpty()) {
            try {
                double w = Double.parseDouble(weightInput);
                userData.setCurrentWeight(w);
                System.out.println("تم تسجيل وزنك الحالي بنجاح! 💪✨");
            } catch (NumberFormatException e) {
                System.out.println("لم يتم تغيير الوزن بسبب إدخال غير رقمي.");
            }
        }
        saveData();
    }

    private static void showMyRewards() {
        System.out.println("\n--- 🎁 صفحة مكافآتي والسجل 🎁 ---");
        List<GoalSquare> squares = userData.getSquares();
        boolean hasCompleted = false;

        for (GoalSquare sq : squares) {
            if (sq.isCompleted()) {
                hasCompleted = true;
                System.out.printf("• المربع %d: %s | تاريخ الإنجاز: %s\n", 
                        sq.getId(), sq.getReward(), sq.getCompletedDate().isEmpty() ? "مؤخراً" : sq.getCompletedDate());
                if (!sq.getNote().isEmpty()) {
                    System.out.println("  ✍️ ملاحظة: " + sq.getNote());
                }
            }
        }

        if (!hasCompleted) {
            System.out.println("لا توجد مكافآت مفتوحة بعد. أنجزي المربعات لتفتحي مكافآتك اللطيفة! 🎀");
        }
        System.out.println("\nإضغطي Enter للعودة القائمة الرئيسية...");
        scanner.nextLine();
    }

    private static void addNewSquare() {
        System.out.println("\n--- ➕ إضافة مربع جديد ---");
        System.out.print("ادخلي وصف المكافأة للمربع الجديد: ");
        String reward = scanner.nextLine().trim();
        if (!reward.isEmpty()) {
            int newId = userData.getSquares().size() + 1;
            userData.getSquares().add(new GoalSquare(newId, reward));
            System.out.println("تمت إضافة المربع رقم " + newId + " بنجاح! 🎀");
            saveData();
        }
    }

    private static void resetData() {
        System.out.print("\n⚠️ هل أنتِ متاكدة من إستعادة ضبط المصنع وإعادة تعيين كافة البيانات؟ (اكتبي 'نعم' للـتأكيد): ");
        String confirm = scanner.nextLine().trim();
        if (confirm.equalsIgnoreCase("نعم")) {
            userData = new UserData();
            saveData();
            System.out.println("تم إعادة تعيين الجدول والبيانات بنجاح! 🌸");
        } else {
            System.out.println("تم إلغاء عملية إعادة التعيين.");
        }
    }

    private static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(userData);
        } catch (IOException e) {
            System.out.println("خطأ أثناء حفظ البيانات: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadData() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                userData = (UserData) ois.readObject();
            } catch (Exception e) {
                System.out.println("تم بدء جلسة جديدة (تعذر تحميل البيانات السابقة).");
                userData = new UserData();
            }
        }
    }
}
