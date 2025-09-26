import tkinter as tk
from tkinter import messagebox
import random
import sys

class CounterApp:
    def __init__(self):
        self.root = tk.Tk()
        self.root.title("计数器工具")
        # 默认窗口尺寸；允许任意缩放（即便缩小导致控件溢出也可）
        self.root.geometry("360x420")
        self.root.minsize(1, 1)
        self.root.resizable(True, True)

        # 设置窗口图标和属性
        self.root.configure(bg='#f0f0f0')

        # 默认不置顶，用户可以切换
        self.is_always_on_top = False

        # 计数器变量
        self.count = 0

        # 悬浮按钮窗口引用（进入悬浮模式时创建）
        self.float_win = None
        self._drag_start = None  # 悬浮窗拖拽起点

        # 随机数生成配置
        self.range_start_var = tk.StringVar(value='1')
        self.range_end_var = tk.StringVar(value='100')
        self.quantity_var = tk.StringVar(value='5')
        self.no_repeat_var = tk.BooleanVar(value=True)
        self.random_result_var = tk.StringVar(value='')

        # 创建界面元素
        self.create_widgets()

        # 绑定键盘事件
        self.root.bind('<Key>', self.on_key_press)
        self.root.focus_set()  # 确保窗口获得焦点以接收键盘事件

        # 设置窗口关闭事件
        self.root.protocol("WM_DELETE_WINDOW", self.on_closing)

        # 设置窗口居中显示
        self.center_window()

    def create_widgets(self):
        # 标题
        title_label = tk.Label(
            self.root,
            text="计数器",
            font=("微软雅黑", 16, "bold"),
            bg='#f0f0f0',
            fg='#333333'
        )
        title_label.pack(pady=10)

        # 计数显示
        self.count_label = tk.Label(
            self.root,
            text=str(self.count),
            font=("Arial", 32, "bold"),
            bg='#f0f0f0',
            fg='#2c3e50',
            width=6
        )
        self.count_label.pack(pady=10)

        # 按钮框架
        button_frame = tk.Frame(self.root, bg='#f0f0f0')
        button_frame.pack(pady=10)

        # -1 按钮
        self.minus_btn = tk.Button(
            button_frame,
            text="-1",
            font=("微软雅黑", 16, "bold"),
            bg='#e74c3c',
            fg='white',
            width=4,
            height=1,
            relief='raised',
            bd=2,
            activebackground='#c0392b',
            activeforeground='white',
            command=self.decrement
        )
        self.minus_btn.pack(side='left', padx=5)

        # +1 按钮
        self.plus_btn = tk.Button(
            button_frame,
            text="+1",
            font=("微软雅黑", 16, "bold"),
            bg='#27ae60',
            fg='white',
            width=4,
            height=1,
            relief='raised',
            bd=2,
            activebackground='#229954',
            activeforeground='white',
            command=self.increment
        )
        self.plus_btn.pack(side='left', padx=5)

        # 功能按钮框架
        func_frame = tk.Frame(self.root, bg='#f0f0f0')
        func_frame.pack(pady=5)

        # 重置按钮

        self.reset_btn = tk.Button(
            func_frame,
            text="重置",
            font=("微软雅黑", 10),
            bg='#95a5a6',
            fg='white',
            width=6,
            relief='raised',
            bd=1,
            activebackground='#7f8c8d',
            activeforeground='white',
            command=self.reset
        )
        self.reset_btn.pack(side='left', padx=3)

        # 置顶切换按钮（复选框样式）
        self.topmost_btn = tk.Button(
            func_frame,
            text="☐ 置顶",
            font=("微软雅黑", 10),
            bg='#95a5a6',
            fg='white',
            width=8,
            relief='raised',
            bd=1,
            activebackground='#7f8c8d',
            activeforeground='white',
            command=self.toggle_topmost
        )
        self.topmost_btn.pack(side='left', padx=3)

        # 悬浮模式按钮
        self.float_mode_btn = tk.Button(
            func_frame,
            text="悬浮",
            font=("微软雅黑", 10),
            bg='#2980b9',
            fg='white',
            width=6,
            relief='raised',
            bd=1,
            activebackground='#1f6390',
            activeforeground='white',
            command=self.enter_float_mode
        )
        self.float_mode_btn.pack(side='left', padx=3)

        # 说明文本
        help_label = tk.Label(
            self.root,
            text="快捷键: ↑/+ 加1, ↓/- 减1, Space 重置, T 置顶, F 悬浮, R 随机",
            font=("微软雅黑", 8),
            bg='#f0f0f0',
            fg='#7f8c8d'
        )
        help_label.pack(pady=(5, 0))

        # 随机数生成区域
        self.create_random_widgets()

    def center_window(self):
        """将窗口居中显示"""
        self.root.update_idletasks()
        width = self.root.winfo_width()
        height = self.root.winfo_height()
        x = (self.root.winfo_screenwidth() // 2) - (width // 2)
        y = (self.root.winfo_screenheight() // 2) - (height // 2)
        self.root.geometry(f'{width}x{height}+{x}+{y}')

    def update_display(self):
        """更新计数显示"""
        self.count_label.config(text=str(self.count))

        # 根据数值改变颜色
        if self.count > 0:
            self.count_label.config(fg='#27ae60')
        elif self.count < 0:
            self.count_label.config(fg='#e74c3c')
        else:
            self.count_label.config(fg='#2c3e50')

        # 悬浮窗同步显示
        if self.float_win is not None and self.float_win.winfo_exists():
            try:
                btn = self.float_win.nametowidget('float_btn')
                btn.config(text=str(self.count))
                # 同步颜色
                if self.count > 0:
                    btn.config(bg='#27ae60')
                elif self.count < 0:
                    btn.config(bg='#e74c3c')
                else:
                    btn.config(bg='#2980b9')
            except Exception:
                pass

    def increment(self):
        """增加计数"""
        self.count += 1
        self.update_display()
        self.flash_button(self.plus_btn)

    def decrement(self):
        """减少计数"""
        self.count -= 1
        self.update_display()
        self.flash_button(self.minus_btn)

    def reset(self):
        """重置计数"""
        self.count = 0
        self.update_display()
        self.flash_button(self.reset_btn)

    def toggle_topmost(self):
        """切换置顶状态"""
        self.is_always_on_top = not self.is_always_on_top

        # 在Windows上需要先设置为False再设置为True来确保生效
        if self.is_always_on_top:
            self.root.attributes('-topmost', False)
            self.root.update()
            self.root.attributes('-topmost', True)
            self.topmost_btn.config(text="☑ 置顶", bg='#27ae60')
            # 确保窗口获得焦点
            self.root.lift()
            self.root.focus_force()
        else:
            self.root.attributes('-topmost', False)
            self.topmost_btn.config(text="☐ 置顶", bg='#95a5a6')

        self.flash_button(self.topmost_btn)

    # ================= 悬浮模式相关 =================
    def enter_float_mode(self):
        """进入悬浮按钮模式：创建一个小的置顶可拖拽按钮窗"""
        if self.float_win is not None and self.float_win.winfo_exists():
            return

        # 隐藏主窗口
        self.root.withdraw()

        self.float_win = tk.Toplevel(self.root)
        self.float_win.overrideredirect(True)  # 无边框
        self.float_win.attributes('-topmost', True)
        self.float_win.configure(bg='#000000')  # 背景不重要，按钮将填满

        # 初始位置：右下角附近
        sw, sh = self.float_win.winfo_screenwidth(), self.float_win.winfo_screenheight()
        w, h = 68, 68
        x, y = sw - w - 24, sh - h - 120
        self.float_win.geometry(f"{w}x{h}+{x}+{y}")

        # 按钮充满窗口
        float_btn = tk.Button(
            self.float_win,
            name='float_btn',
            text=str(self.count),
            font=("微软雅黑", 14, 'bold'),
            bg='#2980b9',
            fg='white',
            relief='raised',
            bd=2,
            activebackground='#1f6390',
            activeforeground='white'
        )
        float_btn.pack(fill='both', expand=True)

        # 鼠标交互：
        # - 左键按下记录坐标；拖拽移动；释放若几乎未移动则视为点击增1
        # - Shift+左键释放 视为减1
        # - 右键返回主界面；双击左键重置
        float_btn.bind('<ButtonPress-1>', self._start_drag)
        float_btn.bind('<B1-Motion>', self._on_drag)
        float_btn.bind('<ButtonRelease-1>', self._on_release_click)
        float_btn.bind('<Shift-ButtonRelease-1>', self._on_release_shift_click)
        float_btn.bind('<Button-3>', lambda e: self.exit_float_mode())  # 右键返回主界面
        float_btn.bind('<Double-Button-1>', lambda e: self._float_reset(e))

        # 键盘支持：Esc 返回
        self.float_win.bind('<Escape>', lambda e: self.exit_float_mode())

        # 确保在最上层
        self.float_win.lift()
        self.float_win.focus_force()

    def exit_float_mode(self):
        """退出悬浮按钮模式，回到主窗口"""
        try:
            if self.float_win is not None and self.float_win.winfo_exists():
                self.float_win.destroy()
        finally:
            self.float_win = None
            # 恢复主窗口
            self.root.deiconify()
            if self.is_always_on_top:
                # 恢复置顶偏好
                self.root.attributes('-topmost', False)
                self.root.update()
                self.root.attributes('-topmost', True)
            self.root.lift()
            self.root.focus_force()

    # 悬浮窗事件处理
    def _float_reset(self, _):
        self.reset()

    def _start_drag(self, event):
        # 记录起点
        self._drag_start = (event.x_root, event.y_root)
        # 记录释放判断时的窗口位置
        if self.float_win:
            self._drag_initial_pos = (self.float_win.winfo_x(), self.float_win.winfo_y())

    def _on_drag(self, event):
        if not self._drag_start or not self.float_win:
            return
        dx = event.x_root - self._drag_start[0]
        dy = event.y_root - self._drag_start[1]
        self._drag_start = (event.x_root, event.y_root)
        # 当前窗口位置
        geom = self.float_win.geometry()
        try:
            size, pos = geom.split('+', 1)
            cur_x, cur_y = map(int, pos.split('+'))
        except Exception:
            cur_x, cur_y = self.float_win.winfo_x(), self.float_win.winfo_y()
        new_x, new_y = cur_x + dx, cur_y + dy
        self.float_win.geometry(f"+{new_x}+{new_y}")

    def _on_release_click(self, event):
        # 判断是否为点击（而非拖拽）
        try:
            ix, iy = getattr(self, '_drag_initial_pos', (self.float_win.winfo_x(), self.float_win.winfo_y()))
            cx, cy = self.float_win.winfo_x(), self.float_win.winfo_y()
            if abs(cx - ix) < 3 and abs(cy - iy) < 3:
                self.increment()
        finally:
            self._drag_start = None

    def _on_release_shift_click(self, event):
        # Shift + 点击 -> 减1（阻止默认增1）
        self.decrement()
        self._drag_start = None
        return "break"

    def flash_button(self, button):
        """按钮闪烁效果"""
        original_bg = button.cget('bg')
        button.config(relief='sunken')
        self.root.after(100, lambda: button.config(relief='raised'))

    def on_key_press(self, event):
        """处理键盘按键事件"""
        key = event.keysym

        if key in ['Up', 'plus', 'equal']:
            self.increment()
        elif key in ['Down', 'minus']:
            self.decrement()
        elif key == 'space':
            self.reset()
        elif key == 't' or key == 'T':
            self.toggle_topmost()
        elif key == 'f' or key == 'F':
            self.enter_float_mode()
        elif key == 'r' or key == 'R':
            self.generate_random()
        elif key == 'Escape':
            self.on_closing()

    # ================= 随机数生成功能 =================
    def create_random_widgets(self):
        # 标题
        title = tk.Label(
            self.root,
            text='随机数生成',
            font=("微软雅黑", 12, 'bold'),
            bg='#f0f0f0',
            fg='#333333'
        )
        title.pack(pady=(8, 4))

        rf = tk.Frame(self.root, bg='#f0f0f0')
        rf.pack(pady=2)

        # 范围输入：起始 - 结束
        tk.Label(rf, text='范围', font=("微软雅黑", 10), bg='#f0f0f0').grid(row=0, column=0, padx=2, pady=2)
        e1 = tk.Entry(rf, textvariable=self.range_start_var, width=6, justify='center')
        e1.grid(row=0, column=1, padx=2, pady=2)
        tk.Label(rf, text='-', font=("微软雅黑", 10), bg='#f0f0f0').grid(row=0, column=2, padx=0, pady=2)
        e2 = tk.Entry(rf, textvariable=self.range_end_var, width=6, justify='center')
        e2.grid(row=0, column=3, padx=2, pady=2)

        # 数量
        tk.Label(rf, text='数量', font=("微软雅黑", 10), bg='#f0f0f0').grid(row=0, column=4, padx=(8,2), pady=2)
        e3 = tk.Entry(rf, textvariable=self.quantity_var, width=6, justify='center')
        e3.grid(row=0, column=5, padx=2, pady=2)

        # 不重复选项
        cb = tk.Checkbutton(rf, text='不重复', variable=self.no_repeat_var, onvalue=True, offvalue=False, bg='#f0f0f0')
        cb.grid(row=0, column=6, padx=(8, 2), pady=2)

        # 生成按钮
        gen_btn = tk.Button(rf, text='生成', font=("微软雅黑", 10), bg='#8e44ad', fg='white', width=6,
                            activebackground='#6d3687', activeforeground='white', command=self.generate_random)
        gen_btn.grid(row=0, column=7, padx=(8, 0), pady=2)

        # 回车快捷触发
        for w in (e1, e2, e3):
            w.bind('<Return>', lambda _e: self.generate_random())

        # 结果显示
        res = tk.Label(self.root, textvariable=self.random_result_var, font=("微软雅黑", 10), bg='#f0f0f0',
                       fg='#2c3e50', wraplength=280, justify='left')
        res.pack(pady=(4, 4))

    def generate_random(self):
        try:
            start = int(self.range_start_var.get().strip())
            end = int(self.range_end_var.get().strip())
            qty = int(self.quantity_var.get().strip())
        except ValueError:
            messagebox.showerror('输入错误', '范围与数量必须为整数，例如 范围: 1 到 100，数量: 5')
            return

        if qty <= 0:
            messagebox.showerror('输入错误', '数量必须为正整数')
            return

        # 允许输入 start > end，自动交换
        if start > end:
            start, end = end, start

        size = end - start + 1
        no_repeat = self.no_repeat_var.get()
        if no_repeat and qty > size:
            messagebox.showerror('输入错误', f'不重复数量不能大于范围大小（当前范围大小为 {size}）')
            return

        if no_repeat:
            nums = random.sample(range(start, end + 1), qty)
        else:
            nums = [random.randint(start, end) for _ in range(qty)]

        # 排序展示，便于查看；复制时使用逗号分隔
        show = ', '.join(map(str, sorted(nums)))
        self.random_result_var.set(f'结果：{show}（已复制）')

        # 复制到剪贴板
        try:
            self.root.clipboard_clear()
            self.root.clipboard_append(','.join(map(str, nums)))
        except Exception:
            pass

    def on_closing(self):
        """窗口关闭事件处理"""
        if messagebox.askokcancel("退出", "确定要退出计数器吗？"):
            self.root.destroy()

    def run(self):
        """启动应用程序"""
        self.root.mainloop()

def main():
    """主函数"""
    try:
        app = CounterApp()
        app.run()
    except Exception as e:
        messagebox.showerror("错误", f"程序运行出错：{str(e)}")

if __name__ == "__main__":
    main()
